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

package com.mijack.zero.framework.dao.memory;

import java.util.Objects;

import com.mijack.zero.framework.dao.Criteria;
import org.apache.commons.beanutils.BeanMap;

/**
 * @author Mi&Jack
 */
public interface CriteriaOperator<C extends Criteria> {


    boolean validateBean(C criteria, BeanMap beanMap);

    static CriteriaOperator<Criteria.EqCriteria> createEqCriteria() {
        return (eqCriteria, beanMap) -> {
            String field = eqCriteria.getField();
            Object targetValue = eqCriteria.getValue();
            Object value = beanMap.get(field);
            return Objects.equals(targetValue, value);
        };
    }

    static CriteriaOperator<Criteria.ExpressionCriteria> createFalseCriteria() {
        return (criteria, beanMap) -> false;
    }

    static CriteriaOperator<Criteria.ExpressionCriteria> createTrueCriteria() {
        return (criteria, beanMap) -> true;
    }

    static CriteriaOperator<Criteria.InCriteria> createInCriteria() {
        return (inCriteria, beanMap) -> {
            String field = inCriteria.getField();
            Object value = beanMap.get(field);
            return inCriteria.getValues().contains(value);
        };
    }

}
