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

package com.mijack.zero.ddd.infrastructure.criteria;

import java.util.Objects;

import org.apache.commons.beanutils.BeanMap;

/**
 * @author Mi&Jack
 */
public interface CriteriaOperator {
    boolean validateBean(BeanMap beanMap);

    static EqCriteriaOperator create(Criteria.EqCriteria eqCriteria) {
        return new EqCriteriaOperator(eqCriteria);
    }

    class NullCriteriaOperator implements CriteriaOperator{
        /**
         * 单例模式
         */
        public static final CriteriaOperator INSTANCE = new NullCriteriaOperator();

        @Override
        public boolean validateBean(BeanMap beanMap) {
            return false;
        }
    }

    class EqCriteriaOperator implements CriteriaOperator {
        private final Criteria.EqCriteria eqCriteria;

        public EqCriteriaOperator(Criteria.EqCriteria eqCriteria) {
            this.eqCriteria = eqCriteria;
        }

        @Override
        public boolean validateBean(BeanMap beanMap) {
            String field = eqCriteria.getField();
            Object targetValue = eqCriteria.getValue();
            Object value = beanMap.get(field);
            return Objects.equals(targetValue, value);
        }
    }
}
