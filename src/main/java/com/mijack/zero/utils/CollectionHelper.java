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
     * @param keys
     * @param <Key>
     * @return
     */
    static <Key> List<Key> toList(Key... keys) {
        if (keys == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(keys);
    }

    /**
     * 返回集合的大小
     *
     * @param collection
     * @param <T>
     * @return
     */
    static <T> int size(Collection<T> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * 返回列表中第一个值，如果列表不足一个，返回默认值，
     *
     * @param list
     * @param defaultValue
     * @param <T>
     * @return
     */
    static <T> T firstValue(List<T> list, T defaultValue) {
        return size(list) != 0 ? list.get(0) : defaultValue;
    }


}
