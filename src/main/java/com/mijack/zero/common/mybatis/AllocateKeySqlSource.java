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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.SqlBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * @author Mi&Jack
 */
public class AllocateKeySqlSource implements SqlSource {
    public static final Logger logger = LoggerFactory.getLogger(AllocateKeySqlSource.class);
    private final Configuration configuration;
    private final String tableName;
    private final Class domainClazz;
    private final Method method;

    public AllocateKeySqlSource(Configuration configuration, String tableName, Class domainClazz, Method method) {
        this.configuration = configuration;
        this.tableName = tableName;
        this.domainClazz = domainClazz;
        this.method = method;
    }


    @Override
    public BoundSql getBoundSql(Object parameterObject) {
//        //
//        INSERT INTO animals (grp,name) VALUES
//                ('mammal','dog'),('mammal','cat'),
//        ('bird','penguin'),('fish','lax'),('mammal','whale'),
//        ('bird','ostrich');
        try {
            Object instance;

            instance = domainClazz.newInstance();

            Field[] declaredFields = domainClazz.getDeclaredFields();
            List<String> list = Lists.newArrayList();
            List<String> values = Lists.newArrayList();
            List<ParameterHolder> parameters = Lists.newArrayList();
            for (Field field : declaredFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                String name = field.getName();
                list.add(name);
                field.setAccessible(true);
                parameters.add(new ParameterHolder(name, field.get(instance), field.getType()));

                values.add("?");
            }
            String sql = new SQL().INSERT_INTO(tableName).INTO_COLUMNS(list.toArray(new String[]{}))
                    .INTO_VALUES(values.toArray(new String[]{}))
                    .toString();
            logger.info("sql = {}", sql);
            StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, sql, buildParameterMap(parameters));
            BoundSql boundSql = staticSqlSource.getBoundSql(parameterObject);
            for (ParameterHolder h : parameters) {
                boundSql.setAdditionalParameter(h.getName(), h.getValue());
            }
            return boundSql;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    //    List<ParameterHolder> parameters = sqlFormatter.getParameters(criteria);
//    BoundSql boundSql = new StaticSqlSource(configuration, sql, buildParameterMap(parameters)).getBoundSql(parameters);
//        for (ParameterHolder h : parameters) {
//        boundSql.setAdditionalParameter(h.getName(), h.getValue());
//    }
//        return boundSql;
//}
//
    private List<ParameterMapping> buildParameterMap(List<ParameterHolder> parameters) {
        List<ParameterMapping> list = new ArrayList<>();
        for (ParameterHolder parameterHolder : parameters) {
            list.add(new ParameterMapping.Builder(configuration, parameterHolder.getName(), parameterHolder.getType()).build());
        }
        return list;
    }
}
