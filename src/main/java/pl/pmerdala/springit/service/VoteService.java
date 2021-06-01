package pl.pmerdala.springit.service;

import org.springframework.stereotype.Service;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.domain.Vote;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.repositories.VoteRepository;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final LinkRepository linkRepository;

    public VoteService(VoteRepository voteRepository, LinkRepository linkRepository) {
        this.voteRepository = voteRepository;
        this.linkRepository = linkRepository;
    }

    public int updateVoteIfCanAndReturnCount(String userLogin, Long linkId, short direction) {
        return linkRepository.findById(linkId).map(link -> {
            if (isCorrectInputData(userLogin, direction)) {
                updateVoteForLink(userLogin, direction, link);
            }
            return link.getVoteCount();
        }).orElse(0);
    }

    private void updateVoteForLink(String userLogin, short direction, Link link) {
        int userVote = voteRepository.voteSumByCreatedByAndLink(userLogin, link);
        if (isEnableChangeVote(direction, userVote)) {
            Vote vote = new Vote(link, direction);
            voteRepository.save(vote);
            link.addVote(vote);
            linkRepository.save(link);
        }
    }

    private boolean isEnableChangeVote(short direction, int userVote) {
        return (userVote > 0 && direction < 0) || (userVote < 0 && direction > 0) || (userVote == 0);
    }

    private boolean isCorrectInputData(String username, short direction) {
        return username != null && Math.abs(direction) == 1;
    }

}
