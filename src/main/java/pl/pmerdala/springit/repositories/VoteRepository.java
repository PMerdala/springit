package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.domain.Vote;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("select sum(direction) from Vote where createdBy = ?1 and link = ?2")
    Integer voteSumByCreatedByAndLinkOrNullIfNotExists(String createdBy, Link link);

    default int voteSumByCreatedByAndLink(String createdBy, Link link) {
        return Optional.ofNullable(voteSumByCreatedByAndLinkOrNullIfNotExists(createdBy, link)).orElse(0);
    }

}
