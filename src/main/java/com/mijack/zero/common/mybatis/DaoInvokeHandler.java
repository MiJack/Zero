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

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.infrastructure.DomainDaoUtils;
import com.mijack.zero.ddd.infrastructure.IDomainDao;
import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import lombok.Getter;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;

/**
 * @author Mi&Jack
 */
public class DaoInvokeHandler<KEY, DOMAIN extends BaseDomain<KEY>, DAO extends IDomainDao<KEY, DOMAIN>> implements InvocationHandler, IDomainDao<KEY, DOMAIN> {
    @Getter
    private final Class<DAO> daoInterface;
    private final JdbcTemplate jdbcTemplate;
    CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = new CompositeCriteriaSqlFormatter();

    public DaoInvokeHandler(Class<DAO> daoInterface, JdbcTemplate jdbcTemplate) {
        this.daoInterface = daoInterface;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        if (method.getDeclaringClass().isAssignableFrom(DaoInvokeHandler.class)) {
            Method memoryMethod = MethodUtils.getAccessibleMethod(getClass(), method);
            if (memoryMethod != null) {
                return memoryMethod.invoke(this, args);
            }
        }
        // 如果这个方法定义在代理接口上
        if (method.getDeclaringClass().isAssignableFrom(daoInterface) && method.isDefault()) {
            return invokeDefaultMethod(method, daoInterface, proxy, args);
        }
        return method.invoke(this, args);
    }

    Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object,
                               @Nullable Object... args) throws Throwable {
        // Because the service interface might not be public, we need to use a MethodHandle lookup
        // that ignores the visibility of the declaringClass.
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, -1)
                .unreflectSpecial(method, declaringClass)
                .bindTo(object)
                .invokeWithArguments(args);
    }

    @Override
    public @NotNull List<DOMAIN> queryList(Criteria criteria) {
        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        String sql = " select * from " + loadTable() + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        return jdbcTemplate.query(sql, getArgs(parameters), this::mapRow);
    }

    private DOMAIN mapRow(ResultSet rs, int rowNum) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long countBy(Criteria criteria) {
        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        String sql = " select count(1) from " + loadTable() + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        return jdbcTemplate.queryForObject(sql, getArgs(parameters), (rs, rowNum) -> rs.getLong(0));
    }

    /**
     * INSERT INTO table_name (column1, column2, column3, ...)
     * VALUES (value1, value2, value3, ...);
     *
     * @param domains 领域对象
     * @return
     */
    @Override
    public long add(List<DOMAIN> domains) {
        try {
            @NotNull Class<DOMAIN> domainClass = getDomainClass();
            Field[] declaredFields = domainClass.getDeclaredFields();
            List<List<ParameterHolder>> holders = new ArrayList<>();
            for (DOMAIN t : domains) {
                List<ParameterHolder> holder = null;
                holder = transformHolder(t, declaredFields);
                holders.add(holder);
            }
            String sql = buildSql(holders);
            return jdbcTemplate.update(sql,getArgsForList(holders));
        } catch (IllegalAccessException e) {
            throw new DaoException(e);
        }
    }

    private Object[] getArgsForList(List<List<ParameterHolder>> holders) {
        throw new UnsupportedOperationException();
    }

    private List<ParameterHolder> transformHolder(@NotNull DOMAIN t, Field[] declaredFields) throws IllegalAccessException {
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
        sb.append(loadTable());
        sb.append(" (");
        sb.append(String.join(" , ", columnNames));
        sb.append(")");
        sb.append(" values ");
        int count = holders.size();
        int columnCount = columnNames.size();
        String valueForOne = IntStream.range(0, columnCount).mapToObj(s -> "?").collect(Collectors.joining(" , ", " ( ", " ) "));
        String values = IntStream.range(0, count).mapToObj(s -> valueForOne).collect(Collectors.joining(" , "));
        sb.append(values);
        return sb.toString();
    }

    /**
     * //UPDATE table_name
     * //SET column1 = value1, column2 = value2, ...
     * //WHERE condition;
     *
     * @param domainHolder
     * @param criteria
     * @return
     */
    @Override
    public long update(DOMAIN domainHolder, Criteria criteria) {
        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        String sql = "update " + loadTable() + " set " + formatSetSql(parameters) + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        return jdbcTemplate.update(sql, getArgs(parameters));

    }

    private Object[] getArgs(List<ParameterHolder> parameters) {
        throw new UnsupportedOperationException();
    }

    private String formatSetSql(List<ParameterHolder> parameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long delete(Criteria criteria) {

        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        String sql = "delete from " + loadTable() + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        return jdbcTemplate.update(sql, getArgs(parameters));
    }

    String loadTable() {
        Table table = daoInterface.getAnnotation(Table.class);
        return table.name();
    }

    @Override
    public @NotNull Class<DOMAIN> getDomainClass() {
        return DomainDaoUtils.getDomainClass(daoInterface);
    }
}
