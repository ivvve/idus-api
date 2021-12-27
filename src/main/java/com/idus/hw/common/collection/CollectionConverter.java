package com.idus.hw.common.collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionConverter {
    public static <K, T> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyExtractor) {
        return collection.stream()
                .collect(Collectors.toMap(
                        keyExtractor,
                        it -> it
                ));
    }

    public static <T, R> List<R> convertAndToList(Collection<T> collection, Function<T, R> converter) {
        return collection.stream()
                .map(converter)
                .collect(Collectors.toList());
    }
}
