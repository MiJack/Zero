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
import java.util.List;

import com.mijack.zero.utils.CollectionHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Mi&Jack
 */
public interface Criteria {


    /**
     * field = value
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    static Criteria eq(String field, Object value) {
        return new EqCriteria(field, value);
    }

    /**
     * field > value
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    static Criteria gt(String field, Object value) {
        return new GtCriteria(field, value);
    }

    /**
     * field >= value
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    static Criteria ge(String field, Object value) {
        return new GeCriteria(field, value);
    }

    /**
     * field < value
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    static Criteria lt(String field, Object value) {
        return new LtCriteria(field, value);
    }

    /**
     * field <= value
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    static Criteria le(String field, Object value) {
        return new LeCriteria(field, value);
    }

    /**
     * field in values
     *
     * @param field  字段
     * @param values 字段值
     * @return
     */
    static Criteria in(String field, List<?> values) {
        return new InCriteria(field, values);
    }

    /**
     * criterion and criterion
     *
     * @param criterion 条件谓词
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
     * criterion or criterion
     *
     * @param criterion 条件谓词
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

    Criteria TRUE = new TrueCriteria();
    Criteria FALSE = new FalseCriteria();

    @Data
    class ExpressionCriteria implements Criteria {
        private final String expression;

        public ExpressionCriteria(String expression) {
            this.expression = expression;
        }
    }

    @Data
    class FieldCriteria implements Criteria {
        private final String field;
        private final Object value;
        private String operation;

        public FieldCriteria(String field, Object value, String operation) {
            this.field = field;
            this.value = value;
            this.operation = operation;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class EqCriteria extends FieldCriteria {

        public EqCriteria(String field, Object value) {
            super(field, value, "=");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class NotEqCriteria extends FieldCriteria {
        public NotEqCriteria(String field, Object value) {
            super(field, value, "!=");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class LikeCriteria extends FieldCriteria {
        public LikeCriteria(String field, Object value) {
            super(field, value, "LIKE");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class NotLikeCriteria extends FieldCriteria {
        public NotLikeCriteria(String field, Object value) {
            super(field, value, "NOT LIKE");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class GtCriteria extends FieldCriteria {

        public GtCriteria(String field, Object value) {
            super(field, value, ">");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class GeCriteria extends FieldCriteria {

        public GeCriteria(String field, Object value) {
            super(field, value, ">=");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class LtCriteria extends FieldCriteria {

        public LtCriteria(String field, Object value) {
            super(field, value, "<");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class LeCriteria extends FieldCriteria {
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
            this.criteria.addAll(CollectionHelper.toList(criterion));
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

    class FalseCriteria extends ExpressionCriteria {
        public FalseCriteria() {
            super("1 = 0");
        }
    }

    class TrueCriteria extends ExpressionCriteria {
        public TrueCriteria() {
            super("1 = 1");
        }
    }

    @Data
    class InCriteria implements Criteria {
        private final String field;
        private final List<?> values;

        public InCriteria(String field, List<?> values) {
            this.field = field;
            this.values = values;
        }
    }
}
