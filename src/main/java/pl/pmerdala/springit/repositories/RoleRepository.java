package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
