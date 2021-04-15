package pl.pmerdala.springit.services;

import java.util.Optional;
import java.util.Set;

public interface ProjectService<T,ID> {
    Set<T> findAll();
    Optional<T> findById(ID id);
    T save(T object);
    void delete(T object);
    void deleteById(ID id);
}
