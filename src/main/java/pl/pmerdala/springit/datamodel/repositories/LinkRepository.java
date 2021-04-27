package pl.pmerdala.springit.datamodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.datamodel.domain.Link;

public interface LinkRepository extends JpaRepository<Link,Long> {
}
