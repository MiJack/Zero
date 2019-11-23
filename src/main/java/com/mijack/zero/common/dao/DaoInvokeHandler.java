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

import static com.mijack.zero.framework.constant.ReflectConstant.TYPE_RESOLVER;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.fasterxml.classmate.ResolvedType;
import com.mijack.zero.framework.constant.ReflectConstant;
import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.exceptions.DaoException;
import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.dao.idao.IDao;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import lombok.Getter;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;

/**
 * @author Mi&Jack
 */
public class DaoInvokeHandler<ID, D extends IdentifiableData<ID, D> & DataHolder<D>, DAO extends BasicDao<ID, D>>
        implements InvocationHandler, BasicDao<ID, D> {

    public static final Logger logger = LoggerFactory.getLogger(DaoInvokeHandler.class);
    private static final String GENERATED_KEY = "GENERATED_KEY";
    @Getter
    private final Class<DAO> daoInterface;
    private final DaoInvokeProxyConfiguration daoInvokeHandlerConfiguration;

    public DaoInvokeHandler(Class<DAO> daoInterface, DaoInvokeProxyConfiguration daoInvokeHandlerConfiguration) {
        this.daoInterface = daoInterface;
        this.daoInvokeHandlerConfiguration = daoInvokeHandlerConfiguration;
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
        IDao<D> dao = findProxyObject(method);
        if (dao != null) {
            return method.invoke(dao, args);
        }
        if (ReflectConstant.findMethod(method, getClass()) != null) {
            return method.invoke(this, args);
        }
        throw new UnsupportedOperationException();
    }

    private IDao<D> findProxyObject(Method method) {
        Map<Class<? extends IDao<?>>, IDao<?>> daoMap = daoInvokeHandlerConfiguration.getDaoMap();
        for (Map.Entry<Class<? extends IDao<?>>, IDao<?>> entry : daoMap.entrySet()) {
            Class<? extends IDao<?>> daoClazz = entry.getKey();

            if (ReflectConstant.findMethod(method, daoClazz) != null) {
                @SuppressWarnings("unchecked")
                IDao<D> dao = (IDao<D>) entry.getValue();
                return dao;
            }
        }
        return null;
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

    // dao 实现部分
    @Override
    @NotNull
    public Class<D> getDataClazz() {
        ResolvedType resolve = TYPE_RESOLVER.resolve(daoInterface);
        if (resolve.isInstanceOf(IDao.class)) {
            List<ResolvedType> resolvedTypes = resolve.typeParametersFor(IDao.class);
            ResolvedType domainType = resolvedTypes.get(0);
            @SuppressWarnings("unchecked")
            Class<D> erasedType = (Class<D>) domainType.getErasedType();
            return erasedType;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public long delete(Criteria criteria) {
        if (isDeletableDo()) {
            return updateDeleteStatus(fixCriteria(criteria));
        } else {
            return deletePyi(criteria);
        }
    }

    private boolean isDeletableDo() {
        return DeletableDo.class.isAssignableFrom(getDataClazz());
    }

    private long updateDeleteStatus(Criteria criteria) {
        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = daoInvokeHandlerConfiguration.getCompositeCriteriaSqlFormatter();
        JdbcTemplate jdbcTemplate = daoInvokeHandlerConfiguration.getJdbcTemplate();
        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        String sql = "update " + loadTable() + " set  " + getDeletedField() + " = 1 " + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        return jdbcTemplate.update(sql, getArgs(parameters));
    }

    private String getDeletedField() {
        return "deleted";
    }

    private long deletePyi(Criteria criteria) {
        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = daoInvokeHandlerConfiguration.getCompositeCriteriaSqlFormatter();
        JdbcTemplate jdbcTemplate = daoInvokeHandlerConfiguration.getJdbcTemplate();
        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        String sql = "delete from " + loadTable() + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        return jdbcTemplate.update(sql, getArgs(parameters));
    }

    @Override
    public List<D> query(Criteria criteria) {
        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = daoInvokeHandlerConfiguration.getCompositeCriteriaSqlFormatter();
        JdbcTemplate jdbcTemplate = daoInvokeHandlerConfiguration.getJdbcTemplate();
        String condition = compositeCriteriaSqlFormatter.toSql(criteria);
        String sql = " select * from " + loadTable() + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        return jdbcTemplate.query(sql, getArgs(parameters), this::mapRow);
    }

    private D mapRow(ResultSet rs, int rowNum) {
        @NotNull Class<D> domainClass = getDataClazz();
        D domain = BeanUtils.instantiateClass(domainClass);
        List<Field> declaredFields = FieldUtils.getAllFieldsList(domainClass);
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            compositeValue(domain, field, rs);
        }
        return domain;
    }

    /**
     * //UPDATE table_name
     * //SET column1 = value1, column2 = value2, ...
     * //WHERE condition;
     *
     * @param domainHolder
     * @param criteria     更新的田间
     * @return 更新的记录数
     */
    @Override
    public <DH extends DataHolder<D>> long update(DH domainHolder, Criteria criteria) {

        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = daoInvokeHandlerConfiguration.getCompositeCriteriaSqlFormatter();
        JdbcTemplate jdbcTemplate = daoInvokeHandlerConfiguration.getJdbcTemplate();
        String condition = compositeCriteriaSqlFormatter.toSql(fixCriteria(criteria));
        Object[] argsFromDomainHolder = getArgsFromDomainHolder(domainHolder);
        List<Object> argsList = Lists.newArrayList();
        argsList.addAll(Lists.newArrayList(argsFromDomainHolder));


        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        Object[] args = getArgs(parameters);
        argsList.addAll(Lists.newArrayList(args));

        String sql = "update " + loadTable() + " set " + formatSetSql(domainHolder) + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        return jdbcTemplate.update(sql, argsList.toArray());

    }


    private <DH extends DataHolder<D>> String formatSetSql(DH domainHolder) {
        List<Field> holders = Lists.newArrayList();
        Field[] declaredFields = getDataClazz().getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if ("id".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object o = field.get(domainHolder);
                if (o != null) {
                    holders.add(field);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException();
            }
        }
        return holders.stream().map(f -> f.getName() + " = ? ").collect(Collectors.joining(" , "));
    }

    @Override
    public long count(Criteria criteria) {
        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = daoInvokeHandlerConfiguration.getCompositeCriteriaSqlFormatter();
        JdbcTemplate jdbcTemplate = daoInvokeHandlerConfiguration.getJdbcTemplate();
        String condition = compositeCriteriaSqlFormatter.toSql(fixCriteria(criteria));
        String sql = " select count(1) from " + loadTable() + (StringUtils.isNotEmpty(condition) ? (" where " + condition) : " ");
        List<ParameterHolder> parameters = compositeCriteriaSqlFormatter.getParameters(criteria);
        Object[] args = getArgs(parameters);
        Long count = jdbcTemplate.queryForObject(sql, args, (rs, rowNum) -> rs.getLong(0));
        return count != null ? count : 0L;
    }

    private Criteria fixCriteria(Criteria criteria) {
        if (isDeletableDo()) {
            return criteria.and(Criteria.nq(getDeletedField(), 1));
        } else {
            return criteria;
        }
    }

    @Override
    public List<ID> allocateIds(int number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ID> insertData(List<? extends DataHolder<D>> list) {
        throw new UnsupportedOperationException();
    }

    private void compositeValue(D domain, Field field, ResultSet rs) {
        try {
            if (field.getType().isAssignableFrom(Integer.class)) {
                field.set(domain, rs.getInt(field.getName()));
                return;
            } else if (field.getType().isAssignableFrom(Long.class)) {
                field.set(domain, rs.getLong(field.getName()));
                return;
            } else if (field.getType().isAssignableFrom(String.class)) {
                field.set(domain, rs.getString(field.getName()));
                return;
            } else if (field.getType().isAssignableFrom(Boolean.class)) {
                field.set(domain, rs.getBoolean(field.getName()));
                return;
            } else if (field.getType().isAssignableFrom(Timestamp.class)) {
                field.set(domain, rs.getTimestamp(field.getName()));
                return;
            }

        } catch (IllegalAccessException | SQLException e) {
            throw new DaoException(e);
        }
        logger.error("compositeValue error: field = {}", field);
        throw new UnsupportedOperationException();
    }

    //
//    /**
//     * INSERT INTO table_name (column1, column2, column3, ...)
//     * VALUES (value1, value2, value3, ...);
//     *
//     * @param domains 领域对象
//     * @return
//     */
//    @Override
//    public long add(List<DOMAIN> domains) {
//        List<DOMAIN> realAdd = Lists.newArrayList();
//        List<DOMAIN> realUpdate = Lists.newArrayList();
//        for (DOMAIN domain : domains) {
//            if (domain.getId() == null) {
//                realAdd.add(domain);
//            } else {
//                realUpdate.add(domain);
//            }
//        }
//        return doSqlAddOp(realAdd) + updateList(realUpdate);
//    }
//
//    private long updateList(List<DOMAIN> realUpdate) {
//        if (CollectionUtils.isEmpty(realUpdate)) {
//            return 0L;
//        }
//        return realUpdate.stream().map(this::update).reduce(Long::sum).orElse(0L);
//    }
//
//    private long doSqlAddOp(List<DOMAIN> domains) {
//        if (CollectionUtils.isEmpty(domains)) {
//            return 0L;
//        }
//        try {
//            @NotNull Class<DOMAIN> domainClass = getDomainClass();
//            Field[] declaredFields = domainClass.getDeclaredFields();
//            List<List<ParameterHolder>> holders = new ArrayList<>();
//            for (DOMAIN t : domains) {
//                List<ParameterHolder> holder = transformHolder(t, declaredFields);
//                if (holder != null) {
//                    holders.add(holder);
//                }
//            }
//            String sql = buildInsertSql(holders);
//            final Object[] argsForList = getArgsForList(holders);
//            jdbcTemplate.update(sql, argsForList);
//            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//            int update = jdbcTemplate.update(con -> {
//                PreparedStatement prepStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                List<ParameterHolder> holderList = holders.stream().flatMap(Collection::stream).collect(Collectors.toList());
//                for (int i = 0; i < holderList.size(); i++) {
//                    ParameterHolder parameterHolder = holderList.get(i);
//                    Class<?> type = parameterHolder.getType();
//                    if (type.isAssignableFrom(Integer.class)) {
//                        prepStmt.setInt(i, (Integer) parameterHolder.getValue());
//                    } else if (type.isAssignableFrom(Timestamp.class)) {
//                        prepStmt.setTimestamp(i, (Timestamp) parameterHolder.getValue());
//                    } else if (type.isAssignableFrom(Boolean.class)) {
//                        prepStmt.setBoolean(i, (Boolean) parameterHolder.getValue());
//                    } else if (type.isAssignableFrom(String.class)) {
//                        prepStmt.setString(i, String.valueOf(parameterHolder.getValue()));
//                    } else {
//                        logger.error("createPreparedStatement error: type = {}", type);
//                        throw new UnsupportedOperationException();
//                    }
//                }
//                return prepStmt;
//            }, generatedKeyHolder);
//            List<Map<String, Object>> keyList = generatedKeyHolder.getKeyList();
//            CollectionHelper.composeList(keyList, domains, (keyMap, domain) -> domain.setId(toKey(keyMap, domainClass)));
//            return update;
//        } catch (IllegalAccessException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    private KEY toKey(Map<String, Object> stringObjectMap, Class<DOMAIN> domainClass) {
//        Class<KEY> keyClazz = DomainDaoUtils.getDomainKeyClazz(domainClass);
//        if (keyClazz != null && keyClazz.isAssignableFrom(Long.class)) {
//            // GENERATED_KEY
//            if (stringObjectMap.containsKey(GENERATED_KEY)) {
//                Object o = stringObjectMap.get(GENERATED_KEY);
//                if (o instanceof BigInteger) {
//                    Long value = ((BigInteger) o).longValue();
//                    @SuppressWarnings("unchecked")
//                    KEY key = (KEY) value;
//                    return key;
//                }
//            }
//        }
//        throw new RuntimeException();
//    }
//
//    private Object[] getArgsForList(List<List<ParameterHolder>> holders) {
//        return holders.stream().flatMap(Collection::stream).map(ParameterHolder::getValue).toArray();
//    }
//
//    private List<ParameterHolder> transformHolder(@NotNull DOMAIN t, Field[] declaredFields) throws IllegalAccessException {
//        List<ParameterHolder> holders = Lists.newArrayList();
//        for (Field field : declaredFields) {
//            if (Modifier.isStatic(field.getModifiers())) {
//                continue;
//            }
//            field.setAccessible(true);
//            Object value = field.get(t);
//            if (value != null) {
//                holders.add(new ParameterHolder(field.getName(), value, field.getType()));
//            }
//        }
//        return holders;
//    }
//
    private String buildInsertSql(List<List<ParameterHolder>> holders) {
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

    //
//
//
    private <DH extends DataHolder<D>> Object[] getArgsFromDomainHolder(DH domainHolder) {
        List<Object> holders = Lists.newArrayList();
        Field[] declaredFields = getDataClazz().getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if ("id".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object o = field.get(domainHolder);
                if (o != null) {
                    holders.add(o);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException();
            }
        }
        return holders.toArray();
    }

    //
    private Object[] getArgs(List<ParameterHolder> parameters) {
        return parameters.stream().map(ParameterHolder::getValue).toArray();
    }

    //
    private String loadTable() {
        Table table = daoInterface.getAnnotation(Table.class);
        return table.name();
    }

}
