/*
 *     Copyright 2019 Mi&Jack
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.mijack.zero.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.mijack.zero.ZeroApplication;
import com.mijack.zero.demo.Repo;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */

public class ArchitectureTest {
    public static final Logger logger = LoggerFactory.getLogger(ArchitectureTest.class);

    @Test
    public void validateArchitectureRule() {
        // 所有class不应该直接依赖Spring的注解
        JavaClasses allClasses = new ClassFileImporter().importClasspath();
        allClasses.that(new DescribedPredicate<JavaClass>("加载所有的接口或者类", null) {
            @Override
            public boolean apply(JavaClass input) {
                Class<?> clazz = input.reflect();
                return !clazz.isAnnotation();
            }
        });

        ArchRule annotationRules = classes().should().accessClassesThat(new DescribedPredicate<JavaClass>("注解不直接依赖于spring") {
            @Override
            public boolean apply(JavaClass input) {

                return !(input.tryGetAnnotationOfType(Service.class).isPresent()
                        || input.tryGetAnnotationOfType(Repository.class).isPresent());
            }
        });
        annotationRules.check(allClasses);
        // 检查领域是否依赖基础领域对象
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mijack.zero.biz.user.domain");
//        ArchRule baseDomainRule = classes().should().implement(BaseDomain.class);
        //  delete字段移至do层
        ArchRule fieldRule = fields().that().areNotFinal().and().areNotStatic()
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