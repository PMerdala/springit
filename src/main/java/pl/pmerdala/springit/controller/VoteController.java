package pl.pmerdala.springit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.pmerdala.springit.domain.Vote;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.repositories.VoteRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class VoteController {
    private final VoteRepository voteRepository;
    private final LinkRepository linkRepository;

    public VoteController(VoteRepository voteRepository, LinkRepository linkRepository) {
        this.voteRepository = voteRepository;
        this.linkRepository = linkRepository;
    }

    @GetMapping("/vote/link/{linkId}/direction/{direction}")
    public int vote(Principal principle, @PathVariable Long linkId, @PathVariable short direction){
        final AtomicInteger voteCount = new AtomicInteger(0);
        linkRepository.findById(linkId).ifPresent(link->{
            if (canChangeVote(getUsername(principle), direction)) {
                int userVote = voteRepository.voteSumByCreatedByAndLink(principle.getName(), link);
                if (isEnableChangeVote(direction, userVote)) {
                    Vote vote = new Vote(link, direction);
                    voteRepository.save(vote);
                    link.addVote(vote);
                    linkRepository.save(link);
                }
            }
            voteCount.set(link.getVoteCount());
        });
        return voteCount.get();
    }

    private String getUsername(Principal principle) {
        return Optional.ofNullable(principle).map(Principal::getName).orElse(null);
    }

    private boolean isEnableChangeVote(short direction, int userVote) {
        return (userVote > 0 && direction < 0) || (userVote < 0 && direction > 0) || (userVote == 0);
    }

    private boolean canChangeVote(String username, short direction) {
        return username != null && Math.abs(direction) == 1;
    }
}
