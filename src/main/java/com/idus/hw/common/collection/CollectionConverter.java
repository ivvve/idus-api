package com.idus.hw.common.collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionConverter {
    public static <K, T> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyExtractor) {
        return collection.stream()
                .collect(Collectors.toMap(
                        it -> keyExtractor.apply(it),
                        it -> it
                ));
    }
}
