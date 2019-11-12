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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

/**
 * @author Mi&Jack
 */
public interface Criteria {
    static Criteria eq(String field, Object value) {
        return new EqCriteria(field, value);
    }

    default Criteria and(Criteria... criterias) {
        if (this instanceof AndCriteria) {
            ((AndCriteria) this).appendCriterias(criterias);
            return this;
        } else {
            return new AndCriteria(this, criterias);
        }
    }

    default Criteria or(Criteria... criterias) {
        if (this instanceof OrCriteria) {
            ((OrCriteria) this).appendCriterias(criterias);
            return this;
        } else {
            return new OrCriteria(this, criterias);
        }
    }

    @Data
    class EqCriteria implements Criteria {
        private final String field;
        private final Object value;

        public EqCriteria(String field, Object value) {
            this.field = field;
            this.value = value;
        }
    }

    @Data
    class AndCriteria implements Criteria {
        private final List<Criteria> criteria = new ArrayList<>();

        public AndCriteria(Criteria criteria, Criteria... criterias) {
            this.criteria.add(criteria);
            appendCriterias(criterias);
        }

        public void appendCriterias(Criteria... criterias) {
            this.criteria.addAll(Arrays.asList(criterias));
        }
    }

    @Data
    class OrCriteria implements Criteria {
        private final List<Criteria> criteria = new ArrayList<>();

        public OrCriteria(Criteria criteria, Criteria... criterias) {
            this.criteria.add(criteria);
            appendCriterias(criterias);
        }

        public void appendCriterias(Criteria... criterias) {
            this.criteria.addAll(Arrays.asList(criterias));
        }
    }
}
