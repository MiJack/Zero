
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

package com.mijack.zero;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.domain.DeletableDomain;
import com.mijack.zero.ddd.infrastructure.IDomainDao;
import com.mijack.zero.ddd.infrastructure.Table;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Mi&Jack
 * @see IDomainDao
 * @see BaseDomain
 * @see DeletableDomain
 */
@Component
public class DomainRepositoryPostProcessor implements BeanPostProcessor {
    public static final Logger logger = LoggerFactory.getLogger(DomainRepositoryPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            Class<?> beanClass = bean.getClass();
            if (beanClass.equals(MapperFactoryBean.class)) {
                MapperFactoryBean mapperFactoryBean = (MapperFactoryBean) bean;
                Class mapperInterface = mapperFactoryBean.getMapperInterface();
                if (!mapperInterface.isAssignableFrom(IDomainDao.class)) {
                    return null;
                }
                Class domainClazz = (Class) ((ParameterizedType) mapperInterface.getGenericInterfaces()[0]).getActualTypeArguments()[1];
                String prefix = mapperInterface.getName();
                addMethod(mapperInterface, domainClazz, mapperFactoryBean, prefix);

                logger.info("class = {}", beanClass);
            }
        }
        return null;
    }

    private void addMethod(Class currentInterface, Class domainClazz, MapperFactoryBean mapperFactoryBean, String prefix) {
        for (Method method : currentInterface.getDeclaredMethods()) {
            if (method.isDefault()) {
                continue;
            }
            logger.info("method: {}", method);
            Configuration configuration = mapperFactoryBean.getSqlSession().getConfiguration();
            String id = prefix + "." + method.getName();

            if (!configuration.getMappedStatementNames().contains(id)) {
                logger.warn("mappedStatement missing : id = {} ", id);

                addMappedStatement(configuration, domainClazz, id, method);
            }
        }
        if (currentInterface.getInterfaces() != null) {
            for (Class superInterface : currentInterface.getInterfaces()) {
                addMethod(superInterface, domainClazz, mapperFactoryBean, prefix);
            }
        }
    }

    private void addMappedStatement(Configuration configuration, Class domainClazz, String id, Method method) {
        configuration.addMappedStatement(buildMappedStatement(configuration, domainClazz, id, method));
    }

    private SqlSource buildSqlSource(Class domainClazz, Method method) {
        Table table = (Table) domainClazz.getAnnotation(Table.class);
        String name = table.name();
        if (add.equals(name)) {
        }
        if (update.equals(name)) {
        }
        if (delete.equals(name)) {
            if (domainClazz.isAssignableFrom(DeletableDomain.class)) {
            } else {

            }
        }
        if (findList.equals(name)) {
        }
        if (allocateKey.equals(name)) {
        }
        throw new UnsupportedOperationException();

    }

    private MappedStatement buildMappedStatement(Configuration configuration, Class domainClazz, String id, Method method) {
        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, id, buildSqlSource(domainClazz, method), buildSqlType(domainClazz, method));
        return builder.build();
    }

    public static final String add = "add";
    public static final String update = "update";
    public static final String delete = "delete";
    public static final String findList = "findList";
    public static final String allocateKey = "allocateKey";

    private SqlCommandType buildSqlType(Class domainClazz, Method method) {
        //  com.mijack.zero.biz.user.infrastructure.dao.UserDao.add
        String name = method.getName();
        if (add.equals(name)) {
            return SqlCommandType.INSERT;
        }
        if (update.equals(name)) {
            return SqlCommandType.UPDATE;
        }
        if (delete.equals(name)) {
            return (domainClazz.isAssignableFrom(DeletableDomain.class)) ? SqlCommandType.UPDATE : SqlCommandType.DELETE;
        }
        if (findList.equals(name)) {
            return SqlCommandType.SELECT;
        }
        if (allocateKey.equals(name)) {
            // todo 确认实现
            return SqlCommandType.SELECT;
        }

        throw new UnsupportedOperationException();
    }


}
