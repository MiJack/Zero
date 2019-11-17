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
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * @author Mi&Jack
 */
public class AddDomainSqlSource implements SqlSource {
    public static final Logger logger = LoggerFactory.getLogger(AddDomainSqlSource.class);
    private final Configuration configuration;
    private final String tableName;
    private final Class<?> domainClazz;
    private final Method method;

    public AddDomainSqlSource(Configuration configuration, String tableName, Class<?> domainClazz, Method method) {
        this.configuration = configuration;
        this.tableName = tableName;
        this.domainClazz = domainClazz;
        this.method = method;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        try {

            Field[] declaredFields = domainClazz.getDeclaredFields();
            List<ParameterHolder> holders = Lists.newArrayList();
            for (Field field : declaredFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                holders.add(new ParameterHolder(field.getName(), parameterObject != null ? field.get(parameterObject) : null, field.getType()));
            }
            holders = holders.stream().filter(new Predicate<ParameterHolder>() {
                @Override
                public boolean test(ParameterHolder parameterHolder) {
                    return false;
                }
            }).collect(Collectors.toList());


            String sql = holders.isEmpty() ? " INSERT INTO " + tableName + "() values()" : buildSql(holders);
            logger.info("sql = {}", sql);
            StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, sql, Collections.emptyList());
            BoundSql boundSql = staticSqlSource.getBoundSql(parameterObject);

            return boundSql;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private String buildSql(List<ParameterHolder> holders) {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(holders.stream().map(ParameterHolder::getName).collect(Collectors.joining(" , ")));
        sb.append(")");
        sb.append(" values ");

        sb.append(" (");
        sb.append(holders.stream().map(s -> "?").collect(Collectors.joining(" , ")));
        sb.append(")");
        return sb.toString();
    }
}
