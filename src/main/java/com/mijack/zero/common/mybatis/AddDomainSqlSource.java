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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * @author Mi&Jack
 */
public class AddDomainSqlSource<T> implements SqlSource {
    public static final Logger logger = LoggerFactory.getLogger(AddDomainSqlSource.class);
    private final Configuration configuration;
    private final String tableName;
    private final Class<T> domainClazz;

    public AddDomainSqlSource(Configuration configuration, String tableName, Class<T> domainClazz) {
        this.configuration = configuration;
        this.tableName = tableName;
        this.domainClazz = domainClazz;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        try {
            // parameterObject => List<? extends BaseDomain>
            @SuppressWarnings("unchecked")
            List<T> list = (List<T>) ((DefaultSqlSession.StrictMap<?>) parameterObject).get("list");
            if (list == null || list.isEmpty()) {
                throw new IllegalArgumentException("parameterObject is empty");
            }
            Field[] declaredFields = domainClazz.getDeclaredFields();
            List<List<ParameterHolder>> holders = new ArrayList<>();
            for (T t : list) {
                List<ParameterHolder> holder = transformHolder(t, declaredFields);
                holders.add(holder);
            }


            String sql = buildSql(holders);
            logger.info("sql = {}", sql);
            StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, sql, Collections.emptyList());

            return staticSqlSource.getBoundSql(parameterObject);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private List<ParameterHolder> transformHolder(@NotNull T t, Field[] declaredFields) throws IllegalAccessException {
        List<ParameterHolder> holders = Lists.newArrayList();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            holders.add(new ParameterHolder(field.getName(), field.get(t), field.getType()));
        }
        return holders;
    }

    private String buildSql(List<List<ParameterHolder>> holders) {
        List<String> columnNames = holders.stream().flatMap(Collection::stream).filter(h -> Objects.nonNull(h.getValue())).map(ParameterHolder::getName).distinct()
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(String.join(" , ", columnNames));
        sb.append(")");
        sb.append(" values ");
        int count = holders.size();
        int columnCount = columnNames.size();
        String valueForOne = IntStream.range(0, columnCount).mapToObj(s -> "?").collect(Collectors.joining(" , ", " ( ", " ) "));
        String values = IntStream.range(0, count).mapToObj(s -> valueForOne).collect(Collectors.joining(" , " ));
        sb.append(values);
        return sb.toString();
    }
}
