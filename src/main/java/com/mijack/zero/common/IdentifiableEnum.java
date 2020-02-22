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

import java.util.Objects;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author Mi&amp;Jack
 */
public interface IdentifiableEnum<E extends Enum<E> & IdentifiableEnum<E>> extends IEnum<Integer> {
    /**
     * 枚举变量的id
     *
     * @return
     */
    int getId();

    @Override
    default Integer getValue() {
        return getId();
    }

    default boolean isThisValue(Integer target) {
        return Objects.equals(target, getValue());
    }
}
