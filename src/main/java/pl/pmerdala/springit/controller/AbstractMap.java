package pl.pmerdala.springit.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractMap<SOURCE,TARGET> {
    abstract protected TARGET map(SOURCE source,Object... args);

    protected List<TARGET> map(List<SOURCE> sources,Object... args){
        return map(sources.stream()).collect(Collectors.toUnmodifiableList());
    }

    protected Set<TARGET> map(Set<SOURCE> sources,Object... args){
        return map(sources.stream()).collect(Collectors.toUnmodifiableSet());
    }

    protected Stream<TARGET> map(Stream<SOURCE> sources,Object... args){
        return sources.map(source -> map(source,args));
    }

}
