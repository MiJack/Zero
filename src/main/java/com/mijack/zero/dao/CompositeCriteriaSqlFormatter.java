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

package com.mijack.zero.dao;

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
    Map<Class<? extends Criteria>, CriteriaFormatter> map = new HashMap<>();

    public CompositeCriteriaSqlFormatter() {
        FieldCriteriaFormatter fieldCriteriaFormatter = new FieldCriteriaFormatter();
        map.put(Criteria.EqCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.NotEqCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.LeCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.LtCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.GeCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.GtCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.LikeCriteria.class, fieldCriteriaFormatter);
        map.put(Criteria.NotLikeCriteria.class, fieldCriteriaFormatter);


        map.put(Criteria.AndCriteria.class, new JoinCriteriaFormatter("AND"));
        map.put(Criteria.OrCriteria.class, new JoinCriteriaFormatter("OR"));
    }

    public String toSql(Criteria criteria) {
        if (criteria == null || map.get(criteria.getClass()) == null) {
            return null;
        }
        return map.get(criteria.getClass()).formatSql(criteria, this);
    }


    public List<Object> getParameters(Criteria criteria) {
        return map.get(criteria.getClass()).getParameters(criteria, this);
    }

    interface CriteriaFormatter<T extends Criteria> {
        /**
         * todo
         *
         * @param criteria
         * @param compositeFormatter
         * @return
         */
        String formatSql(T criteria, CompositeCriteriaSqlFormatter compositeFormatter);

        /**
         * todo
         *
         * @param criteria
         * @param compositeFormatter
         * @return
         */
        List<Object> getParameters(T criteria, CompositeCriteriaSqlFormatter compositeFormatter);
    }

    private class FieldCriteriaFormatter implements CriteriaFormatter<Criteria.FieldCriteria> {

        @Override
        public String formatSql(Criteria.FieldCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getField() + " " + criteria.getOperation() + " ?";
        }

        @Override
        public List<Object> getParameters(Criteria.FieldCriteria criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return Lists.newArrayList(criteria.getValue());
        }
    }

    private class JoinCriteriaFormatter<T extends Criteria.JoinCriteria<T>> implements CriteriaFormatter<T> {
        private String join;

        public JoinCriteriaFormatter(String join) {
            this.join = join;
        }

        @Override
        public String formatSql(T criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getCriteria().stream().map(compositeFormatter::toSql).collect(Collectors.joining(" " + join + " "));
        }

        @Override
        public List<Object> getParameters(T criteria, CompositeCriteriaSqlFormatter compositeFormatter) {
            return criteria.getCriteria().stream().map(compositeFormatter::getParameters).collect(Collectors.toList());
        }
    }

}
