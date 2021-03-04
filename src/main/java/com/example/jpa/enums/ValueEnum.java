package com.example.jpa.enums;

import java.util.stream.Stream;

/**
 * Interface for value enum.
 * @param <T> value type
 */
public interface ValueEnum<T> {
    static <V, E extends ValueEnum<V>> E valueToEnum(Class<E> enumType, V value){
        return Stream.of(enumType.getEnumConstants())
                .filter( item -> item.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown database value: " + value));
    }

    T getValue();
}
