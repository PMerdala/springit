package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.domain.Link;

public interface LinkRepository extends JpaRepository<Link,Long> {
}
