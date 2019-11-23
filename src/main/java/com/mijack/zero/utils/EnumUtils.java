package com.mijack.zero.utils;


import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Function;

import com.mijack.zero.common.enums.IdentifiableEnum;

/**
 * @author yuanyujie
 */
public interface EnumUtils {
    static <E extends Enum<E> & IdentifiableEnum<E>> E idOf(int id, Class<E> clazz) {
        return idOf(id, clazz, null);
    }

    static <E extends Enum<E> & IdentifiableEnum<E>> E idOf(int id, Class<E> clazz, E defaultValue) {
        return keyOf(id, IdentifiableEnum::getId, clazz, defaultValue);
    }

    static <K, E extends Enum<E>> E keyOf(K k, Function<E, K> keyMapper, Class<E> clazz) {
        return keyOf(k, keyMapper, clazz, null);
    }

    static <E extends Enum<E>, K> E keyOf(K k, Function<E, K> keyMapper, Class<E> clazz, E defaultValue) {
        for (E e : EnumSet.allOf(clazz)) {
            if (Objects.equals(k, keyMapper.apply(e))) {
                return e;
            }
        }
        return defaultValue;
    }
}
