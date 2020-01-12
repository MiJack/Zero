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

import com.mijack.zero.app.common.web.mvc.ApiController;
import com.mijack.zero.framework.ddd.Dao;
import com.mijack.zero.framework.ddd.Factory;
import com.mijack.zero.framework.ddd.Repo;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.mijack.zero.biz")
public class ArchitectureTest {
    @ArchTest
    public static final ArchRule ANNOTATION_SERVICE_RULE = classes()
            .that().resideInAPackage("com.mijack.zero.biz..service").and().haveNameMatching(".*Service*")
            .should().notBeAnnotatedWith(Service.class)
            .andShould().beAnnotatedWith(com.mijack.zero.framework.ddd.Service.class);

    @ArchTest
    public static final ArchRule ANNOTATION_FACTORY_RULE = classes()
            .that().resideInAPackage("com.mijack.zero.biz..factory").and().haveNameMatching(".*Factory*")
            .should().beAnnotatedWith(Factory.class);

    @ArchTest
    public static final ArchRule ANNOTATION_DAO_RULE = classes()
            .that().resideInAPackage("com.mijack.zero.biz..dao").and().haveNameMatching(".*Dao*")
            .should().beAnnotatedWith(Dao.class);

    @ArchTest
    public static final ArchRule ANNOTATION_REPO_RULE = classes()
            .that().resideInAPackage("com.mijack.zero.biz..repository").and().haveNameMatching(".*Repo*")
            .should().beAnnotatedWith(Repo.class);

    // 所有的Controller只依赖于UserCase
    @ArchTest
    public static final ArchRule CONTROLLER_USER_CASE_RULE = classes()
            .that().haveNameMatching(".*Controller")
            .should().resideInAPackage("..ui.web").andShould()
            .beAnnotatedWith(ApiController.class)
            .andShould()
            .notBeAnnotatedWith(Controller.class);
    // 所有Controller方法的返回值必须输dto
}