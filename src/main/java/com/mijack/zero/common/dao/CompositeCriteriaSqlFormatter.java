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


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.Criteria.ExpressionCriteria;
import com.mijack.zero.framework.dao.Criteria.InCriteria;
import com.mijack.zero.framework.dao.Criteria.EqCriteria;
import com.mijack.zero.framework.dao.Criteria.NotEqCriteria;
import com.mijack.zero.framework.dao.Criteria.LeCriteria;
import com.mijack.zero.framework.dao.Criteria.GtCriteria;
import com.mijack.zero.framework.dao.Criteria.JoinCriteria;
import com.mijack.zero.framework.dao.Criteria.GeCriteria;
import com.mijack.zero.framework.dao.Criteria.FalseCriteria;
import com.mijack.zero.framework.dao.Criteria.LikeCriteria;
import com.mijack.zero.framework.dao.Criteria.TrueCriteria;
import com.mijack.zero.framework.dao.Criteria.AndCriteria;
import com.mijack.zero.framework.dao.Criteria.OrCriteria;
import com.mijack.zero.framework.dao.Criteria.FieldCriteria;
import com.mijack.zero.framework.dao.Criteria.NotLikeCriteria;
import com.mijack.zero.framework.dao.Criteria.LtCriteria;


import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 * @author Mi&Jack
 */
@Component
public class CompositeCriteriaSqlFormatter {
    final Map<Class<? extends Criteria>, CriteriaFormatter<? extends Criteria>> map = new HashMap<>();

    public CompositeCriteriaSqlFormatter() {
        FieldCriteriaFormatter fieldCriteriaFormatter = new FieldCriteriaFormatter();
        registerCriteriaFormatter(EqCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(NotEqCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(LeCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(LtCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(GeCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(GtCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(LikeCriteria.class, fieldCriteriaFormatter);
        registerCriteriaFormatter(NotLikeCriteria.class, fieldCriteriaFormatter);

        JoinCriteriaFormatter and = new JoinCriteriaFormatter("AND");
        registerCriteriaFormatter(AndCriteria.class, and);
        JoinCriteriaFormatter or = new JoinCriteriaFormatter("OR");
        registerCriteriaFormatter(OrCriteria.class, or);

        ExpressionCriteriaFormatter expressionCriteriaFormatter = new ExpressionCriteriaFormatter();
        registerCriteriaFormatter(FalseCriteria.class, expressionCriteriaFormatter);
        registerCriteriaFormatter(TrueCriteria.class, expressionCriteriaFormatter);
        registerCriteriaFormatter(InCriteria.class, new InCriteriaFormatter());
    }

    public void registerCriteriaFormatter(Class<? extends Criteria> criteriaClass,
                                          CriteriaFormatter<? extends Criteria> criteriaFormatter) {
        map.put(criteriaClass, criteriaFormatter);
    }

    public <C extends Criteria> String toSql(C criteria) {
        @SuppressWarnings("unchecked")
        CriteriaFormatter<C> criteriaFormatter = (CriteriaFormatter<C>) map.get(criteria.getClass());
        if (criteriaFormatter == null) {
            throw new UnsupportedOperationException("criteria " + criteria + " can't be transformed to sql");
        }
        return criteriaFormatter.formatSql(criteria, this);
    }


    public <C extends Criteria> List<ParameterHolder> getParameters(C criteria) {
        @SuppressWarnings("unchecked")
        CriteriaFormatter<C> criteriaFormatter = (CriteriaFormatter<C>) map.get(criteria.getClass());
        if (criteriaFormatter == null) {
            return Collections.emptyList();
        }
        return criteriaFormatter.getParameters(criteria, this);
    }

    interface CriteriaFormatter<T extends Criteria> {
        /**
         * 将Criteria格式化sql
         *
         * @param criteria           查询条件
         * @param compositeFormatter 组合的CriteriaFormatter
         * @return 格式以后的sql
         */
        String formatSql(T criteria, CompositeCriteriaSqlFormatter compositeFormatter);

        /**
         * 获取Criteria中所有的参数
         *
         * @param criteria           查询条件
         * @param compositeFormatter 组合的CriteriaFormatter
         * @return Criteria中所有的参数
         */
        List<ParameterHolder> getParameters(T criteria, CompositeCriteriaSqlFormatter compositeFormatter);
    }

    private static class FieldCriteriaFormatter implements CriteriaFormatter<FieldCriteria> {
        @Override
        public String formatSql(FieldCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getField() + " " + criteria.getOperation() + " ?";
        }

        @Override
        public List<ParameterHolder> getParameters(FieldCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            Object value = criteria.getValue();
            Class<?> clazz = value != null ? value.getClass() : null;
            return Lists.newArrayList(new ParameterHolder(criteria.getField(), value, clazz));
        }
    }

    private static class JoinCriteriaFormatter implements CriteriaFormatter<JoinCriteria> {
        private final String join;

        public JoinCriteriaFormatter(String join) {
            this.join = join;
        }

        @Override
        public String formatSql(JoinCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getCriteria().stream().map(compositeFormatter::toSql).collect(Collectors.joining(" " + join + " "));
        }

        @Override
        public List<ParameterHolder> getParameters(JoinCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getCriteria().stream().map(compositeFormatter::getParameters)
                    .flatMap(Collection::stream).collect(Collectors.toList());
        }
    }

    private static class ExpressionCriteriaFormatter implements CriteriaFormatter<ExpressionCriteria> {
        @Override
        public String formatSql(ExpressionCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getExpression();
        }

        @Override
        public List<ParameterHolder> getParameters(ExpressionCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return Collections.emptyList();
        }
    }

    private static class InCriteriaFormatter implements CriteriaFormatter<InCriteria> {

        @Override
        public String formatSql(InCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getField() + " in ( " + criteria.getValues().stream().map(s -> " ? ").collect(Collectors.joining(" , ")) + " )";
        }

        @Override
        public List<ParameterHolder> getParameters(InCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getValues().stream().map(v -> new ParameterHolder(null, v, v.getClass())).collect(Collectors.toList());
        }
    }
}
