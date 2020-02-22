/*
 *     Copyright 2020 Mi&Jack
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

package com.mijack.zero.common;


import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Function;


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
    static <E extends Enum<E> & IdentifiableEnum<E>> E idOfEnum(int id, Class<E> clazz) {
        return idOfEnum(id, clazz, null);
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
    static <E extends Enum<E> & IdentifiableEnum<E>> E idOfEnum(int id, Class<E> clazz, E defaultValue) {
        for (E e : EnumSet.allOf(clazz)) {
            if (Objects.equals(id, e.getId())) {
                return e;
            }
        }
        return defaultValue;
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
    static <K, E extends Enum<E>> E keyOfEnum(K k, Function<E, K> keyMapper, Class<E> clazz) {
        return keyOfEnum(k, keyMapper, clazz, null);
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
    static <E extends Enum<E>, K> E keyOfEnum(K k, Function<E, K> keyMapper, Class<E> clazz, E defaultValue) {
        for (E e : EnumSet.allOf(clazz)) {
            if (Objects.equals(k, keyMapper.apply(e))) {
                return e;
            }
        }
        return defaultValue;
    }

    /**
     * 列举给定枚举类的所有枚举值
     *
     * @param clazz
     * @param <E>
     * @return
     */
    static <E extends Enum<E>> Collection<E> listEnums(Class<E> clazz) {
        return EnumSet.allOf(clazz);
    }

    static <R extends Enum<R> & IdentifiableEnum<R> & IDescEnum<R>> EnumDto toEnumDto(R item) {
        EnumDto enumDto = new EnumDto();
        enumDto.setCode(item.getValue());
        enumDto.setDesc(item.getDesc());
        enumDto.setName(item.name());
        return enumDto;
    }
}
