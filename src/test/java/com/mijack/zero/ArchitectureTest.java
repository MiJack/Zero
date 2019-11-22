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

package com.mijack.zero;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mi&Jack
 */

public class ArchitectureTest {
    public static final Logger logger = LoggerFactory.getLogger(ArchitectureTest.class);

    @Test
    public void validateArchitectureRule() {
        // 检查领域是否依赖基础领域对象
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mijack.zero.biz.user.domain");
//        ArchRule baseDomainRule = classes().should().implement(BaseDomain.class);
        //  delete字段移至do层
        ArchRule fieldRule = fields().that().areNotFinal().and().areNotStatic()
                .and().doNotHaveName("deleted")
                .should().notHaveRawType(new DescribedPredicate<JavaClass>("领域对象的非静态非final字段不应该为Primitive类型（delete字段除外）") {
                    @Override
                    public boolean apply(JavaClass input) {
                        return input.isPrimitive();
                    }
                });
//        baseDomainRule.because("领域对象应该实现接口BaseDomain").check(importedClasses);
        fieldRule.because("领域对象的非静态非final字段不应该为Primitive类型(delete字段除外)").check(importedClasses);
    }
}