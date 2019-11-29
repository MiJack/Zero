/*
 *     Copyright 2019 Mi&Jack
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.mijack.zero.utils;


import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Function;

import com.mijack.zero.common.enums.IdentifiableEnum;

/**
 * @author Mi&amp;Jack
 */
public interface EnumUtils {
    /**
     * 给定id，返回目标枚举中id相同的枚举变量
     *
     * @param id
     * @param clazz
     * @param <E>
     * @return
     */
    static <E extends Enum<E> & IdentifiableEnum<E>> E idOf(int id, Class<E> clazz) {
        return idOf(id, clazz, null);
    }

    /**
     * 给定id，返回目标枚举中id相同的枚举变量
     *
     * @param id
     * @param clazz
     * @param defaultValue
     * @param <E>
     * @return
     */
    static <E extends Enum<E> & IdentifiableEnum<E>> E idOf(int id, Class<E> clazz, E defaultValue) {
        return keyOf(id, IdentifiableEnum::getId, clazz, defaultValue);
    }

    /**
     * 给定key，返回目标枚举中key相同的枚举变量
     *
     * @param k
     * @param keyMapper
     * @param clazz
     * @param <K>
     * @param <E>
     * @return
     */
    static <K, E extends Enum<E>> E keyOf(K k, Function<E, K> keyMapper, Class<E> clazz) {
        return keyOf(k, keyMapper, clazz, null);
    }

    /**
     * 给定key，返回目标枚举中key相同的枚举变量
     *
     * @param k
     * @param keyMapper
     * @param clazz
     * @param defaultValue
     * @param <E>
     * @param <K>
     * @return
     */
    static <E extends Enum<E>, K> E keyOf(K k, Function<E, K> keyMapper, Class<E> clazz, E defaultValue) {
        for (E e : EnumSet.allOf(clazz)) {
            if (Objects.equals(k, keyMapper.apply(e))) {
                return e;
            }
        }
        return defaultValue;
    }
}
