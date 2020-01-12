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

package com.mijack.zero.app.common;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mijack.zero.app.utils.CollectionHelper;


/**
 * 断言工具类
 *
 * @author Mi&amp;Jack
 */
public interface Assert {
    /**
     * 判断给定对象是否为空，若对象不为空，抛出异常
     *
     * @param o 待判断的对象
     */
    static void isNull(Object o) {
        isNull(o, () -> createIllegalArgumentException("object should be null"));
    }

    /**
     * 判断给定对象是否为空，若对象不为空，抛出异常
     *
     * @param o        待判断的对象
     * @param supplier 指定抛出的异常
     */
    static void isNull(Object o, Supplier<? extends RuntimeException> supplier) {
        state(o == null, supplier);
    }

    /**
     * 判断给定对象是否不为空，若对象为空，抛出异常
     *
     * @param o 待判断的对象
     */
    static void notNull(Object o) {
        notNull(o, () -> createIllegalArgumentException("object should be null"));
    }

    /**
     * 判断给定对象是否不为空，若对象为空，抛出异常
     *
     * @param o        待判断的对象
     * @param supplier 指定抛出的异常
     */
    static void notNull(Object o, Supplier<? extends RuntimeException> supplier) {
        state(o != null, supplier);
    }

    /**
     * 判断给定两个集合大小是否相同
     *
     * @param c1 待判断的集合 c1
     * @param c2 待判断的集合 c2
     */
    static void sameSize(Collection<?> c1, Collection<?> c2) {
        sameSize(c1, c2, () -> createIllegalArgumentException("object should be null"));
    }

    /**
     * 判断给定两个集合大小是否相同
     *
     * @param c1       待判断的集合 c1
     * @param c2       待判断的集合 c2
     * @param supplier 待抛出的异常
     */
    static void sameSize(Collection<?> c1, Collection<?> c2, Supplier<? extends RuntimeException> supplier) {
        state(CollectionHelper.size(c1) == CollectionHelper.size(c2), supplier);
    }

    /**
     * 判断当前状态，若对象为空，抛出异常
     *
     * @param state    待判断的状态
     * @param supplier 指定抛出的异常
     */
    static void state(boolean state, Supplier<? extends RuntimeException> supplier) {
        if (state) {
            return;
        }
        throw supplier.get();
    }

    /**
     * 创建IllegalArgumentException的默认方法
     *
     * @param message 异常信息
     * @return 生成的Exception
     */
    static IllegalArgumentException createIllegalArgumentException(String message) {
        return new IllegalArgumentException(message);
    }

    /**
     * 给定字符串不为空
     *
     * @param str
     * @param supplier
     */
    static void notEmpty(String str, Supplier<? extends RuntimeException> supplier) {
        state(str != null && str.length() > 0, supplier);
    }

    /**
     * 给定字符串不为空
     *
     * @param str
     */
    static void notEmpty(String str) {
        notEmpty(str, () -> createIllegalArgumentException("字符串为空"));
    }

    /**
     * 给定的两个值相同
     *
     * @param source 比较值
     * @param target 目标值
     */
    static void equals(Object source, Object target) {
        equals(source, target, () -> createIllegalArgumentException("source != target"));
    }

    /**
     * 给定的两个值相同
     *
     * @param source 比较值
     * @param target 目标值
     * @param supplier
     */
    static void equals(@Nullable Object source,@Nullable Object target, Supplier<? extends RuntimeException> supplier) {
        state(Objects.equals(source, target), supplier);
    }
}
