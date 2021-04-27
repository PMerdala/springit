package pl.pmerdala.springit.datamodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.datamodel.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
