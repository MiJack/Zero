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

package com.mijack.zero.framework.dao.memory;

import static com.mijack.zero.framework.constant.ReflectConstant.TYPE_RESOLVER;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.classmate.ResolvedType;
import com.mijack.zero.framework.dao.idao.IDao;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import org.apache.commons.beanutils.MethodUtils;

/**
 * @author Mi&amp;Jack
 */
public class MemoryDaoProxy<ID, D extends IdentifiableData<ID, D> & DataHolder<D>, DAO extends IDao<D>> implements InvocationHandler {
    public static <ID, D extends IdentifiableData<ID, D> & DataHolder<D>, DAO extends IDao<D>>
    DAO proxyForDao(Class<DAO> daoClazz, IDomainKeyGenerator<ID, D> domainKeyGenerator) {
        @SuppressWarnings("unchecked")
        DAO domainDao = (DAO) Proxy.newProxyInstance(daoClazz.getClassLoader(), new Class[]{daoClazz},
                new MemoryDaoProxy<>(daoClazz, domainKeyGenerator));
        return domainDao;
    }

    public static <D extends IdentifiableData<Long, D> & DataHolder<D>, DAO extends IDao<D>>
    DAO defaultProxyForDao(Class<DAO> daoClazz) {
        return proxyForDao(daoClazz, database -> database.size() + 1L);
    }

    private final Class<DAO> daoClazz;
    private final MemoryDao<ID, D> memoryDao;

    public MemoryDaoProxy(@NotNull Class<DAO> daoClazz, @NotNull IDomainKeyGenerator<ID, D> domainKeyGenerator) {
        this.daoClazz = daoClazz;
        Class<D> dataClazz = getDomainClass(daoClazz);
        this.memoryDao = new MemoryDao<>(dataClazz, domainKeyGenerator);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        if (method.getDeclaringClass().isAssignableFrom(getClass())) {
            Method memoryMethod = MethodUtils.getAccessibleMethod(getClass(), method);
            if (memoryMethod != null) {
                return memoryMethod.invoke(this, args);
            }
        }      // 如果这个方法定义在代理接口上
        if (method.getDeclaringClass().isAssignableFrom(daoClazz)) {
            if (method.isDefault()) {
                return invokeDefaultMethod(method, daoClazz, proxy, args);
            }
        }
        return method.invoke(memoryDao, args);
    }

    Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object,
                               Object... args) throws Throwable {
        // Because the service interface might not be public, we need to use a MethodHandle lookup
        // that ignores the visibility of the declaringClass.
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, -1 /* trusted */)
                .unreflectSpecial(method, declaringClass)
                .bindTo(object)
                .invokeWithArguments(args);
    }

    Class<D> getDomainClass(Class<?> daoClazz) {
        ResolvedType resolve = TYPE_RESOLVER.resolve(daoClazz);
        if (resolve.isInstanceOf(IDao.class)) {
            List<ResolvedType> resolvedTypes = resolve.typeParametersFor(IDao.class);
            ResolvedType domainType = resolvedTypes.get(0);
            @SuppressWarnings("unchecked")
            Class<D> erasedType = (Class<D>) domainType.getErasedType();
            return erasedType;
        }
        return null;
    }
}
