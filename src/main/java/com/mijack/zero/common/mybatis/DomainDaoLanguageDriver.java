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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * @author Mi&Jack
 */
public class DomainDaoLanguageDriver implements LanguageDriver {
    final CompositeCriteriaSqlFormatter sqlFormatter = new CompositeCriteriaSqlFormatter();

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        if (!(parameterObject instanceof Criteria)) {
            return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        }
        return new ParameterHandler() {
            @Override
            public Object getParameterObject() {
                return parameterObject;
            }

            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
                Criteria criteria = (Criteria) parameterObject;
                List<ParameterHolder> parameters = sqlFormatter.getParameters(criteria);
                Configuration configuration = mappedStatement.getConfiguration();
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                for (int i = 0; i < parameters.size(); i++) {
                    ParameterHolder parameter = parameters.get(i);
                    Object parameterValue = parameter.getValue();
                    @SuppressWarnings("unchecked")
                    Class<? super Object> parameterValueClazz = (Class<? super Object>) parameterValue.getClass();
                    TypeHandler<? super Object> typeHandler = typeHandlerRegistry.getTypeHandler(parameterValueClazz);
                    typeHandler.setParameter(ps, i + 1, parameterValue, boundSql.getParameterMappings().get(i).getJdbcType());
                }
            }
        };
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        throw new UnsupportedOperationException();
    }
}
