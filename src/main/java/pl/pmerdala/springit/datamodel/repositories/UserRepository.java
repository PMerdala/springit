package pl.pmerdala.springit.datamodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.datamodel.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
