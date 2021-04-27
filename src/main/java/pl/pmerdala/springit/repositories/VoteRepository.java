package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
