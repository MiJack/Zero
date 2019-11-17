
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

package com.mijack.zero.common.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.infrastructure.dao.UserDao;
import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.domain.DeletableDomain;
import com.mijack.zero.ddd.infrastructure.DomainDaoUtils;
import com.mijack.zero.ddd.infrastructure.IDomainDao;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

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
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        if (beanClass.equals(MapperFactoryBean.class)) {
            MapperFactoryBean<?> mapperFactoryBean = (MapperFactoryBean<?>) bean;
            Class<?> mapperInterface = mapperFactoryBean.getMapperInterface();
            if (!IDomainDao.class.isAssignableFrom(mapperInterface)) {
                return null;
            }
            @SuppressWarnings("unchecked")
            Class<? extends IDomainDao<?, ? extends BaseDomain<?>>> daoInterface = (Class<? extends IDomainDao<?, ?>>) mapperInterface;
            Table table = daoInterface.getAnnotation(Table.class);
            String tableName = table.name();
            @SuppressWarnings("unchecked")
            Class<? extends BaseDomain<?>> domainClazz = DomainDaoUtils.getDomainClass(daoInterface);
            if (domainClazz == null) {
                return null;
            }
            String daoClazzName = daoInterface.getName();
            List<Method> methods = Lists.newArrayList();
            loadMethod(daoInterface, methods);
            Configuration configuration = mapperFactoryBean.getSqlSession().getConfiguration();
            for (Method method : methods) {
                logger.info("method = {}", method);
                addMappedStatement(configuration, method, tableName, domainClazz, daoClazzName);
            }

            logger.info("class = {}", beanClass);
        }
        return null;
    }

    private void addMappedStatement(Configuration configuration, Method method, String tableName,
                                    Class<? extends BaseDomain<?>> domainClazz, String daoClazzName) {
        String id = daoClazzName + "." + method.getName();
        LanguageDriver languageDriver = loadLanguageDriver(method, configuration);
        SqlSource sqlSource = buildSqlSource(domainClazz, configuration, tableName, method);
        SqlCommandType sqlType = buildSqlType(domainClazz, method);
        if (sqlSource == null || sqlType == null) {
            return;
        }
        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, id, sqlSource, sqlType);
        builder.lang(languageDriver);
        if (METHOD_ADD.equals(method.getName())) {
            Jdbc3KeyGenerator jdbc3KeyGenerator = new Jdbc3KeyGenerator();
            builder.keyGenerator(jdbc3KeyGenerator);

            builder.resultMaps(Lists.newArrayList());
        } else {
            builder.resultMaps(buildResultMap(configuration, domainClazz));
        }
        configuration.addMappedStatement(builder.build());
    }

    private LanguageDriver loadLanguageDriver(Method method, Configuration configuration) {
        logger.info("loadLanguageDriver info: method = {}", method);
        return configuration.getLanguageDriver(DomainDaoLanguageDriver.class);
    }

    private List<ResultMap> buildResultMap(Configuration configuration, Class<? extends BaseDomain<?>> domainClazz) {
        List<ResultMap> resultMaps = Lists.newArrayList();
        List<ResultMapping> resultMappings = Lists.newArrayList();
        for (Field field : domainClazz.getDeclaredFields()) {
            String property = field.getName();
            String column = field.getName();
            ResultMapping resultMapping = new ResultMapping.Builder(configuration, property, column, field.getType()).build();
            resultMappings.add(resultMapping);
        }
        ResultMap resultMap = new ResultMap.Builder(configuration, domainClazz.getName(), domainClazz, resultMappings).build();
        resultMaps.add(resultMap);
        return resultMaps;
    }

    private void loadMethod(Class<?> currentInterface, List<Method> methods) {
        for (Method method : currentInterface.getDeclaredMethods()) {
            if (method.isDefault()) {
                continue;
            }
            methods.add(method);
        }
        if (currentInterface.getInterfaces() != null) {
            for (Class<?> superInterface : currentInterface.getInterfaces()) {
                loadMethod(superInterface, methods);
            }
        }
    }

    private SqlSource buildSqlSource(Class<? extends BaseDomain<?>> domainClazz, Configuration configuration, String tableName, Method method) {
        String methodName = method.getName();
        if (METHOD_ADD.equals(methodName)) {
            return new AddDomainSqlSource(configuration, tableName, domainClazz, method);
        }
        if (METHOD_UPDATE.equals(methodName)) {
            return new UpdateDomainSqlSource(configuration, tableName, domainClazz, method);
        }
        if (METHOD_DELETE.equals(methodName)) {
            if (DomainDaoUtils.isDeletableDomain(domainClazz)) {
                return new ListDomainSqlSource("update #{table}  set deleted = 1  ", configuration, tableName, domainClazz, method);
            } else {
                return new ListDomainSqlSource("delete from #{table} ", configuration, tableName, domainClazz, method);
            }
        }
        if (METHOD_QUERY_LIST.equals(methodName)) {
            return new ListDomainSqlSource("select * from #{table} ", configuration, tableName, domainClazz, method);
        }
        return null;

    }

    public static final String METHOD_ADD = "add";
    public static final String METHOD_UPDATE = "update";
    public static final String METHOD_DELETE = "delete";
    public static final String METHOD_QUERY_LIST = "queryList";

    private SqlCommandType buildSqlType(Class<? extends BaseDomain<?>> domainClazz, Method method) {
        String name = method.getName();
        if (METHOD_ADD.equals(name)) {
            return SqlCommandType.INSERT;
        }
        if (METHOD_UPDATE.equals(name)) {
            return SqlCommandType.UPDATE;
        }
        if (METHOD_DELETE.equals(name)) {
            return (domainClazz.isAssignableFrom(DeletableDomain.class)) ? SqlCommandType.UPDATE : SqlCommandType.DELETE;
        }
        if (METHOD_QUERY_LIST.equals(name)) {
            return SqlCommandType.SELECT;
        }

        return null;
    }

    public static void main(String[] args) {
        Class<User> domainClass = DomainDaoUtils.getDomainClass(UserDao.class);
        logger.info("domainClass = {}", domainClass);
    }
}
