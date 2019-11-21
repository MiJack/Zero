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

package com.mijack.zero.framework.dao.factory;

import static com.mijack.zero.framework.constant.ReflectConstant.TYPE_RESOLVER;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.classmate.ResolvedType;
import com.mijack.zero.framework.dao.factory.memory.MemoryBasicDao;
import com.mijack.zero.framework.dao.idao.IDao;
import com.mijack.zero.framework.dao.idata.Data;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import org.apache.commons.beanutils.MethodUtils;

/**
 * @author Mi&Jack
 */
public class DaoProxy<D extends Data<D>, DAO extends IDao<D>> implements InvocationHandler {
    private Class<DAO> daoClazz;
    private Class<D> dataClazz;
    private MemoryBasicDao<?, ? extends IdentifiableData<?, ? extends Data<?>>, ? extends DataHolder<?>> memoryBasicDao;
    private Map<Class<? extends IDao<D>>, IDao<D>> daoMap = new HashMap<>();

    public DaoProxy(Class<DAO> daoClazz) {
        this.daoClazz = daoClazz;
        this.dataClazz = getDomainClass(daoClazz);
        memoryBasicDao = new MemoryBasicDao<>(dataClazz);
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
        return method.invoke(memoryBasicDao, args);
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
            ResolvedType domainType = resolvedTypes.get(1);
            @SuppressWarnings("unchecked")
            Class<D> erasedType = (Class<D>) domainType.getErasedType();
            return erasedType;
        }
        return null;
    }
}
