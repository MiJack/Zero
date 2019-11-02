/*
 *    Copyright 2019 Mi&Jack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mijack.zero.common;

import java.util.function.Supplier;


/**
 * 断言工具类
 *
 * @author Mi&Jack
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
     * @param msg
     * @return
     */
    static IllegalArgumentException createIllegalArgumentException(String msg) {
        return new IllegalArgumentException(msg);
    }

}
