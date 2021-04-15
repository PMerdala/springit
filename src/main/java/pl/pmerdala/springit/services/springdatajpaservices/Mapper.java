package pl.pmerdala.springit.services.springdatajpaservices;

import java.util.Optional;

public interface Mapper<S, T> {
    default T map(S source) {
        return map(Optional.ofNullable(source)).orElse(null);
    }

    default S reversMap(T source) {
        return reversMap(Optional.ofNullable(source)).orElse(null);
    }

    Optional<T> map(Optional<S> source);

    Optional<S> reversMap(Optional<T> source);
}
