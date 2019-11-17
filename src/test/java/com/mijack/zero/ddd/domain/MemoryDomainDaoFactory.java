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

package com.mijack.zero.ddd.domain;

import java.lang.reflect.Proxy;

import com.mijack.zero.ddd.infrastructure.criteria.CriteriaFilter;
import com.mijack.zero.ddd.infrastructure.IDomainDao;
import com.mijack.zero.ddd.infrastructure.MemoryDomainDao;

/**
 * @author Mi&Jack
 */
public class MemoryDomainDaoFactory {
    public static <KEY, DOMAIN extends BaseDomain<KEY>, DOMAIN_DAO extends IDomainDao<KEY, DOMAIN>>
    DOMAIN_DAO proxyForDao(Class<DOMAIN_DAO> daoInterface, IDomainKeyGenerator<KEY, DOMAIN> domainKeyGenerator) {
        @SuppressWarnings("unchecked")
        DOMAIN_DAO domainDao = (DOMAIN_DAO) Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class[]{daoInterface},
                new MemoryDomainDao<>(daoInterface, domainKeyGenerator, new CriteriaFilter()));
        return domainDao;
    }
}
