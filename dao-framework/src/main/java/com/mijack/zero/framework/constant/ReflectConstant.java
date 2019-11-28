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

package com.mijack.zero.framework.constant;

import java.lang.reflect.Method;
import java.util.Objects;

import com.fasterxml.classmate.TypeResolver;

/**
 * @author Mi&amp;Jack
 */
public class ReflectConstant {
    private ReflectConstant() {
    }

    public static final TypeResolver TYPE_RESOLVER = new TypeResolver();

    public static Method findMethod(Method method, Class<?> clazz) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (!declaringClass.isAssignableFrom(clazz)) {
            return null;
        }
        Method[] declaredMethods = clazz.getMethods();
        for (Method m : declaredMethods) {
            if (isSameSign(m, method)) {
                return m;
            }
        }
        return null;
    }

    private static boolean isSameSign(Method method1, Method method2) {
        if (!Objects.equals(method1.getName(), method2.getName())) {
            return false;
        }
        return true;
    }
}
