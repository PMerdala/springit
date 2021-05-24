package pl.pmerdala.springit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.pmerdala.springit.domain.Vote;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.repositories.VoteRepository;

import java.security.Principal;
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
            if (principle!=null && (direction==-1 || direction==1)) {
                voteRepository.findByCreatedByAndLinkAndEnable(principle.getName(), link,true).ifPresentOrElse(vote -> {
                    if (vote.getDirection() != direction){
                        link.removeVote(vote);
                        vote.setEnable(false);
                        voteRepository.save(vote);
                        linkRepository.save(link);
                    }
                }, () -> {
                    Vote vote = new Vote(link, direction);
                    voteRepository.save(vote);
                    link.addVote(vote);
                    linkRepository.save(link);
                });
            }
            voteCount.set(link.getVoteCount());
        });
        return voteCount.get();
    }
}
