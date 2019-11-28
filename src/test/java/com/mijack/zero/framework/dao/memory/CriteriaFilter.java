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

/**
 * @author Mi&amp;Jack
 */

import com.mijack.zero.framework.dao.Criteria;
import org.apache.commons.beanutils.BeanMap;

/**
 * @author Mi&amp;Jack
 */
public class CriteriaFilter {
    public <T extends Criteria> boolean doCriteria(Object domain, T criteria) {
        if (domain == null) {
            return false;
        }
        BeanMap beanMap = new BeanMap(domain);
        @SuppressWarnings("unchecked")
        CriteriaOperator<T> c = (CriteriaOperator<T>) CriteriaOperatorFactory.loadCriteriaOperator(criteria);
        return c.validateBean(criteria, beanMap);
    }
}
