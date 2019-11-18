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

import static com.mijack.zero.ddd.infrastructure.criteria.Criteria.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
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
        map.put(EqCriteria.class, fieldCriteriaFormatter);
        map.put(NotEqCriteria.class, fieldCriteriaFormatter);
        map.put(LeCriteria.class, fieldCriteriaFormatter);
        map.put(LtCriteria.class, fieldCriteriaFormatter);
        map.put(GeCriteria.class, fieldCriteriaFormatter);
        map.put(GtCriteria.class, fieldCriteriaFormatter);
        map.put(LikeCriteria.class, fieldCriteriaFormatter);
        map.put(NotLikeCriteria.class, fieldCriteriaFormatter);

        JoinCriteriaFormatter<AndCriteria> and = new JoinCriteriaFormatter<>("AND");
        map.put(AndCriteria.class, and);
        JoinCriteriaFormatter<OrCriteria> or = new JoinCriteriaFormatter<>("OR");
        map.put(OrCriteria.class, or);

        ExpressionCriteriaFormatter expressionCriteriaFormatter = new ExpressionCriteriaFormatter();
        map.put(FalseCriteria.class, expressionCriteriaFormatter);
        map.put(TrueCriteria.class, expressionCriteriaFormatter);
    }

    public <C extends Criteria> String toSql(C criteria) {
        @SuppressWarnings("unchecked")
        CriteriaFormatter<C> criteriaFormatter = (CriteriaFormatter<C>) map.get(criteria.getClass());
        if (criteriaFormatter == null) {
            return null;
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

    private static class FieldCriteriaFormatter implements CriteriaFormatter<FieldCriteria<?>> {
        @Override
        public String formatSql(FieldCriteria<?> criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getField() + " " + criteria.getOperation() + " ?";
        }

        @Override
        public List<ParameterHolder> getParameters(FieldCriteria<?> criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            Object value = criteria.getValue();
            Class<?> clazz = value != null ? value.getClass() : null;
            return Lists.newArrayList(new ParameterHolder(criteria.getField(), value, clazz));
        }
    }

    private static class JoinCriteriaFormatter<T extends JoinCriteria<T>> implements CriteriaFormatter<T> {
        private final String join;

        public JoinCriteriaFormatter(String join) {
            this.join = join;
        }

        @Override
        public String formatSql(T criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getCriteria().stream().map(compositeFormatter::toSql).collect(Collectors.joining(" " + join + " "));
        }

        @Override
        public List<ParameterHolder> getParameters(T criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getCriteria().stream().map(compositeFormatter::getParameters)
                    .flatMap(Collection::stream).collect(Collectors.toList());
        }
    }

    private static class ExpressionCriteriaFormatter implements CriteriaFormatter<ExpressionCriteria<?>> {
        @Override
        public String formatSql(ExpressionCriteria<?> criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getExpression();
        }

        @Override
        public List<ParameterHolder> getParameters(ExpressionCriteria<?> criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return Collections.emptyList();
        }
    }
}
