package org.orekyuu.nozomi.infrastructure.datasource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Collector2 {
    static <T, R> Collector<T, ?, List<R>> mappingToList(Function<T, R> mapper) {
        return Collectors.mapping(mapper, Collectors.toList());
    }

    static <T, R> Collector<T, ?, Optional<R>> mappingToOptional(Function<T, R> mapper) {
        return Collectors.mapping(mapper, toOptional());
    }

    static <T> Collector<T, ?, Optional<T>> toOptional() {
        return Collector.<T, AtomicReference<T>, Optional<T>>of(
                AtomicReference::new,
                (acc, t) -> acc.compareAndSet(null, t),
                (a, b) -> {
                    a.compareAndSet(null, b.get());
                    return a;
                },
                acc -> Optional.ofNullable(acc.get()),
                Collector.Characteristics.CONCURRENT
        );
    }
}
