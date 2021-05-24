package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.domain.Vote;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByCreatedByAndLinkAndEnable(String createdBy, Link link,boolean enable);


}
