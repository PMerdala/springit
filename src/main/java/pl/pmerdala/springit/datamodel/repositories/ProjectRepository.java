package pl.pmerdala.springit.datamodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository<T,ID> extends JpaRepository<T, ID> {
}
