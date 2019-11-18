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

import static com.mijack.zero.common.constant.ReflectConstant.TYPE_RESOLVER;

import java.util.List;

import com.fasterxml.classmate.ResolvedType;
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
    static <D extends BaseDomain<?>> Class<D> getDomainClass(Class<?> daoClazz) {
        ResolvedType resolve = TYPE_RESOLVER.resolve(daoClazz);
        if (resolve.isInstanceOf(IDomainDao.class)) {
            List<ResolvedType> resolvedTypes = resolve.typeParametersFor(IDomainDao.class);
            ResolvedType domainType = resolvedTypes.get(1);
            return (Class<D>) domainType.getErasedType();
        }
        return null;
    }

    /**
     * 判断对象是否为DeletableDomain
     *
     * @param clazz 待判断的类型
     * @return 是/否
     * @see DeletableDomain
     */
    static boolean isDeletableDomain(Class<?> clazz) {
        return DeletableDomain.class.isAssignableFrom(clazz);
    }

    /**
     * 判断对象是否为BaseDomain
     *
     * @param clazz 待判断的类型
     * @return 是/否
     * @see BaseDomain
     */
    static boolean isDomain(Class<?> clazz) {
        return BaseDomain.class.isAssignableFrom(clazz);
    }

    /**
     * 获取Domain类主键类型的Class对象
     *
     * @param domainClazz Domain类
     * @return Domain类主键类型的Class对象
     */
    static <K> Class<K> getDomainKeyClazz(Class<?> domainClazz) {
        if (isDomain(domainClazz)) {
            ResolvedType resolve = TYPE_RESOLVER.resolve(domainClazz);
            List<ResolvedType> resolvedTypes = resolve.typeParametersFor(BaseDomain.class);
            @SuppressWarnings("unchecked")
            Class<K> domainType = (Class<K>) resolvedTypes.get(0).getErasedType();
            return domainType;
        }
        return null;
    }

}
