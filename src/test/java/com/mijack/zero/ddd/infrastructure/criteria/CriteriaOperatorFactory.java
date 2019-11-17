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

/**
 * @author Mi&Jack
 */
public class CriteriaOperatorFactory {
    public static CriteriaOperator loadCriteriaOperator(Criteria criteria) {
        if (criteria == null) {
            return CriteriaOperator.NullCriteriaOperator.INSTANCE;
        }
        if (criteria instanceof Criteria.EqCriteria) {
            return CriteriaOperator.create((Criteria.EqCriteria) criteria);
        }
        return CriteriaOperator.NullCriteriaOperator.INSTANCE;
    }
}
