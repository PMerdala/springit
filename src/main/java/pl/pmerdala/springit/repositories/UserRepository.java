package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
