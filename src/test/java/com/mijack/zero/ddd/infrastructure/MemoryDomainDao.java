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

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.domain.DeletableDomain;
import com.mijack.zero.ddd.domain.IDomainKeyGenerator;
import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import com.mijack.zero.ddd.infrastructure.criteria.CriteriaFilter;
import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @param <Key>    领域对象的主键类型
 * @param <Domain> 领域对象
 * @author Mi&Jack
 */
public class MemoryDomainDao<Key, Domain extends BaseDomain<Key>, DomainDao extends IDomainDao<Key, Domain>> implements IDomainDao<Key, Domain>, InvocationHandler {
    private Map<Key, Domain> domainMap = Maps.newHashMap();
    private Class<DomainDao> daoInterface;
    private IDomainKeyGenerator<Key, Domain> domainKeyGenerator;
    private CriteriaFilter criteriaFilter;

    public MemoryDomainDao(Class<DomainDao> daoInterface, IDomainKeyGenerator<Key, Domain> domainKeyGenerator, CriteriaFilter criteriaFilter) {
        this.daoInterface = daoInterface;
        this.domainKeyGenerator = domainKeyGenerator;
        this.criteriaFilter = criteriaFilter;
    }

    @Override
    public @NotNull List<Domain> list(List<Key> keys) {
        return keys.stream().filter(Objects::nonNull).map(key -> domainMap.get(key)).filter(this::isValid).collect(Collectors.toList());
    }

    @Override
    public Long countBy(Criteria criteria) {
        return (long) queryList(criteria).size();
    }

    @Override
    public @NotNull List<Domain> queryList(Criteria criteria) {
        List<Domain> list = Lists.newArrayList();
        for (Domain domain : domainMap.values()) {
            if (isValid(domain)) {
                if (criteriaFilter.doCriteria(domain, criteria)) {
                    list.add(domain);
                }
            }
        }
        return list;
    }

    protected boolean isValid(Domain domain) {
        if (domain == null) {
            return false;
        }
        if (domain instanceof DeletableDomain) {
            return !((DeletableDomain) domain).isDeleted();
        }
        return true;
    }

    @Override
    public long add(List<Domain> domains) {
        for (Domain domain : domains) {
            if (domainMap.containsKey(domain.getId())) {
                continue;
            }
            domainMap.put(domain.getId(), domain);
        }
        return domains.size();
    }

    @Override
    public long update(List<Domain> domains) {
        return domains.stream().filter(domain -> domainMap.containsKey(domain.getId()))
                .filter(this::isValid)
                .map(domain -> domainMap.put(domain.getId(), domain)).count();
    }

    @Override
    public long delete(List<Key> keys) {
        return keys.stream().filter(key -> domainMap.containsKey(key))
                .map(key -> {
                    Domain domain = domainMap.get(key);
                    if (domain instanceof DeletableDomain) {
                        ((DeletableDomain) domain).setDeleted(true);
                    } else {
                        domainMap.remove(key);
                    }
                    return domain;
                }).count();
    }

    @Override
    public Key allocateKey() {
        return domainKeyGenerator.allocateKey(domainMap);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        if (method.getDeclaringClass().isAssignableFrom(MemoryDomainDao.class)) {
            Method memoryMethod = MethodUtils.getAccessibleMethod(getClass(), method);
            if (memoryMethod != null) {
                return memoryMethod.invoke(this, args);
            }
        }      // 如果这个方法定义在代理接口上
        if (method.getDeclaringClass().isAssignableFrom(daoInterface)) {
            if (method.isDefault()) {
                return invokeDefaultMethod(method, daoInterface, proxy, args);
            }
        }
        return method.invoke(this, args);
    }

    Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object,
                               @Nullable Object... args) throws Throwable {
        // Because the service interface might not be public, we need to use a MethodHandle lookup
        // that ignores the visibility of the declaringClass.
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, -1 /* trusted */)
                .unreflectSpecial(method, declaringClass)
                .bindTo(object)
                .invokeWithArguments(args);
    }

    @Override
    public @NotNull Class<Domain> getDomainClass() {
        return DomainDaoUtils.getDomainClass(daoInterface);
    }
}
