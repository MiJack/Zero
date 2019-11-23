/*
 * Copyright 2019 Mi&Jack
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mijack.zero.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import com.mijack.zero.common.Assert;

/**
 * 集合工具类
 *
 * @author Mi&Jack
 */
public interface CollectionHelper {
    /**
     * 将不定的参数转化成list
     * <note> 如果keys为空，返回{@link Collections#emptyList()}</note>
     *
     * @param items 列表
     * @param <T>   泛型
     * @return list
     */
    @SafeVarargs
    static <T> List<T> toList(T... items) {
        if (items == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(items);
    }

    /**
     * 返回集合的大小
     *
     * @param collection 列表
     * @param <T>        泛型
     * @return 集合的大小
     */
    static <T> int size(Collection<T> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * 返回列表中第一个值，如果列表不足一个，返回默认值，
     *
     * @param list         列表
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return 列表中第一个值
     */
    static <T> T firstValue(List<T> list, T defaultValue) {
        return size(list) != 0 ? list.get(0) : defaultValue;
    }


}
