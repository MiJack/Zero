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

package com.mijack.zero.ddd.infrastructure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.domain.DeletableDomain;

/**
 * @author Mi&Jack
 */
public interface DomainDaoUtils {

    /**
     * 获取IDomainDao的Domain Class
     *
     * @param daoClazz dao类
     * @param <D>      Domain Class
     * @return IDomainDao的Domain Class
     * @throws UnsupportedOperationException daoClazz 不是IDomainDao类
     * @see BaseDomain
     * @see IDomainDao
     */
    @SuppressWarnings("unchecked")
    @NotNull
    static <D extends BaseDomain<?>> Class<D> getDomainClass(Class<? super IDomainDao<?, D>> daoClazz) {
        Type[] genericInterfaces = daoClazz.getGenericInterfaces();
        if (genericInterfaces != null && genericInterfaces.length > 0) {
            Type genericInterface = genericInterfaces[0];
            ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            Type actualTypeArgument = parameterizedType.getActualTypeArguments()[1];
            if (actualTypeArgument instanceof Class) {
                Class<?> clazz = (Class<?>) actualTypeArgument;
                if (clazz.isAssignableFrom(BaseDomain.class)) {
                    @SuppressWarnings("unchecked")
                    Class<D> result = (Class<D>) clazz;
                    return result;
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 判断对象是否为DeletableDomain
     *
     * @param clazz 待判断的类型
     * @return 是/否
     * @see DeletableDomain
     */
    static boolean isDeletableDomain(Class<?> clazz) {
        return clazz.isAssignableFrom(DeletableDomain.class);
    }

    /**
     * 获取Domain类主键类型的Class对象
     *
     * @param domainClazz Domain类
     * @param <D>         Domain类
     * @return Domain类主键类型的Class对象
     */
    static <K, D extends BaseDomain<K>> Class<K> getDomainKeyClazz(Class<D> domainClazz) {
        Type[] genericInterfaces = domainClazz.getGenericInterfaces();
        if (genericInterfaces != null && genericInterfaces.length > 0) {
            Type genericInterface = genericInterfaces[0];
            ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
            if (actualTypeArgument instanceof Class) {
                @SuppressWarnings("unchecked")
                Class<K> result = (Class<K>) actualTypeArgument;
                return result;
            }
        }
        return null;
    }

}
