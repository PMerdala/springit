package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.repositories.ProjectRepository;
import pl.pmerdala.springit.services.ProjectService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AbstractProjectSDJService<T, S, ID> implements ProjectService<T, ID> {

    private final ProjectRepository<S, ID> repository;
    private final Mapper<S, T> mapper;

    public AbstractProjectSDJService(ProjectRepository<S, ID> repository, Mapper<S, T> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Set<T> findAll() {
        return repository.findAll().stream().map(mapper::map).collect(Collectors.toSet());
    }

    @Override
    public Optional<T> findById(ID id) {
        return mapper.map(repository.findById(id));
    }

    @Override
    public T save(T object) {
        return mapper.map(repository.save(mapper.reversMap(object)));
    }

    @Override
    public void delete(T object) {
        repository.delete(mapper.reversMap(object));
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    protected ProjectRepository<S, ID> getRepository() {
        return repository;
    }

    protected Mapper<S, T> getMapper() {
        return mapper;
    }
}
