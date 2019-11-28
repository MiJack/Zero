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

package com.mijack.zero.common.dao;

import java.lang.reflect.Proxy;

import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Mi&Jack
 */
public class DaoInvokeProxyFactory< D extends IdentifiableData<Long, D> & DataHolder<D>, DAO extends BasicDao<Long, D>>
        implements FactoryBean<DAO>, ApplicationContextAware {
    private final Class<DAO> daoClazz;
    private ApplicationContext applicationContext;

    public DaoInvokeProxyFactory(Class<DAO> daoClazz) {
        this.daoClazz = daoClazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DAO getObject() {
        DaoInvokeProxyConfiguration daoInvokeHandlerConfiguration = applicationContext.getBean(DaoInvokeProxyConfiguration.class);
        return (DAO) Proxy.newProxyInstance(daoClazz.getClassLoader(), new Class[]{daoClazz},
                new DaoInvokeHandler<>(daoClazz, daoInvokeHandlerConfiguration));
    }

    @Override
    public Class<?> getObjectType() {
        return daoClazz;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
