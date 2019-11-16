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
    /**
     * field = value
     *
     * @param field
     * @param value
     * @return
     */
    static Criteria eq(String field, Object value) {
        return new EqCriteria(field, value);
    }

    /**
     * field > value
     *
     * @param field
     * @param value
     * @return
     */
    static Criteria gt(String field, Object value) {
        return new GtCriteria(field, value);
    }

    /**
     * field >= value
     *
     * @param field
     * @param value
     * @return
     */
    static Criteria ge(String field, Object value) {
        return new GeCriteria(field, value);
    }

    /**
     * field < value
     *
     * @param field
     * @param value
     * @return
     */
    static Criteria lt(String field, Object value) {
        return new LtCriteria(field, value);
    }

    /**
     * field <= value
     *
     * @param field
     * @param value
     * @return
     */
    static Criteria le(String field, Object value) {
        return new LeCriteria(field, value);
    }

    /**
     * @param criterion
     * @return
     */
    default Criteria and(Criteria... criterion) {
        if (this instanceof AndCriteria) {
            ((AndCriteria) this).appendCriterion(criterion);
            return this;
        } else {
            AndCriteria andCriteria = new AndCriteria(this);
            andCriteria.appendCriterion(criterion);
            return andCriteria;
        }
    }

    /**
     * @param criterion
     * @return
     */
    default Criteria or(Criteria... criterion) {
        if (this instanceof OrCriteria) {
            ((OrCriteria) this).appendCriterion(criterion);
            return this;
        } else {
            OrCriteria orCriteria = new OrCriteria(this);
            orCriteria.appendCriterion(criterion);
            return orCriteria;
        }
    }

    @Data
    class FieldCriteria<T extends FieldCriteria<T>> implements Criteria {
        private final String field;
        private final Object value;
        private String operation;

        public FieldCriteria(String field, Object value, String operation) {
            this.field = field;
            this.value = value;
            this.operation = operation;
        }
    }

    @Data
    class EqCriteria extends FieldCriteria<EqCriteria> {

        public EqCriteria(String field, Object value) {
            super(field, value, "=");
        }
    }

    @Data
    class NotEqCriteria extends FieldCriteria<NotEqCriteria> {
        public NotEqCriteria(String field, Object value) {
            super(field, value, "!=");
        }
    }

    @Data
    class LikeCriteria extends FieldCriteria<LikeCriteria> {
        public LikeCriteria(String field, Object value) {
            super(field, value, "LIKE");
        }
    }

    @Data
    class NotLikeCriteria extends FieldCriteria<NotLikeCriteria> {
        public NotLikeCriteria(String field, Object value) {
            super(field, value, "NOT LIKE");
        }
    }

    @Data
    class GtCriteria extends FieldCriteria<GtCriteria> {

        public GtCriteria(String field, Object value) {
            super(field, value, ">");
        }
    }

    @Data
    class GeCriteria extends FieldCriteria<GeCriteria> {

        public GeCriteria(String field, Object value) {
            super(field, value, ">=");
        }
    }

    @Data
    class LtCriteria extends FieldCriteria<LtCriteria> {

        public LtCriteria(String field, Object value) {
            super(field, value, "<");
        }
    }

    @Data
    class LeCriteria extends FieldCriteria<LeCriteria> {
        public LeCriteria(String field, Object value) {
            super(field, value, "<=");
        }
    }

    @Data
    class JoinCriteria<T extends JoinCriteria<T>> implements Criteria {
        private final List<Criteria> criteria = new ArrayList<>();

        public JoinCriteria(Criteria... criterion) {
            appendCriterion(criterion);
        }

        public void appendCriterion(Criteria... criterion) {
            this.criteria.addAll(Arrays.asList(criterion));
        }
    }

    class AndCriteria extends JoinCriteria<AndCriteria> {
        public AndCriteria(Criteria... criterion) {
            super(criterion);
        }
    }

    class OrCriteria extends JoinCriteria<OrCriteria> {
        public OrCriteria(Criteria... criterion) {
            super(criterion);
        }
    }
}
