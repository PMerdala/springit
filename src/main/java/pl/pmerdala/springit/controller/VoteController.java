package pl.pmerdala.springit.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.pmerdala.springit.service.VoteService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/vote/link/{linkId}/direction/{direction}")
    public int vote(Principal principle, @PathVariable Long linkId, @PathVariable short direction) {
        return voteService.updateVoteIfCanAndReturnCount(getUsername(principle), linkId, direction);
    }

    private String getUsername(Principal principle) {
        return Optional.ofNullable(principle).map(Principal::getName).orElse(null);
    }

}
