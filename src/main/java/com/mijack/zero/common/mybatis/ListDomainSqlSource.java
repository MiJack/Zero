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

import java.util.ArrayList;
import java.util.List;

import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;


/**
 * @author Mi&Jack
 */
public class ListDomainSqlSource implements SqlSource {
    private final String sqlPrefix;
    private final Configuration configuration;
    private final String tableName;
    final CompositeCriteriaSqlFormatter sqlFormatter = new CompositeCriteriaSqlFormatter();

    public ListDomainSqlSource(String sqlPrefix, Configuration configuration, String tableName) {
        this.sqlPrefix = sqlPrefix;
        this.configuration = configuration;
        this.tableName = tableName;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        String sql = sqlPrefix.replace("#{table}", tableName);
        if (!(parameterObject instanceof Criteria)) {
            return null;
        }
        Criteria criteria = (Criteria) parameterObject;
        String query = sqlFormatter.toSql(criteria);
        if (StringUtils.isNoneEmpty(query)) {
            sql += " WHERE " + query;
        }
        List<ParameterHolder> parameters = sqlFormatter.getParameters(criteria);
        BoundSql boundSql = new StaticSqlSource(configuration, sql, buildParameterMap(parameters)).getBoundSql(parameters);
        for (ParameterHolder h : parameters) {
            boundSql.setAdditionalParameter(h.getName(), h.getValue());
        }
        return boundSql;
    }

    private List<ParameterMapping> buildParameterMap(List<ParameterHolder> parameters) {
        List<ParameterMapping> list = new ArrayList<>();
        for (ParameterHolder parameterHolder : parameters) {
            list.add(new ParameterMapping.Builder(configuration, parameterHolder.getName(), parameterHolder.getValue().getClass()).build());
        }
        return list;
    }
}
