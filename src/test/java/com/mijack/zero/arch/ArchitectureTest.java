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

import com.mijack.zero.framework.ddd.Dao;
import com.mijack.zero.framework.ddd.Factory;
import com.mijack.zero.framework.ddd.Repo;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.mijack.zero.biz")
public class ArchitectureTest {
    @ArchTest
    public static final ArchRule ANNOTATION_SERVICE_RULE = classes().that().haveNameMatching(".*Service*").should()
            .notBeAnnotatedWith(Service.class)
            .andShould().beAnnotatedWith(com.mijack.zero.framework.ddd.Service.class)
            .as("Service 应该依赖于Service(DDD)，而不是");
    @ArchTest
    public static final ArchRule ANNOTATION_FACTORY_RULE = classes().that().haveNameMatching(".*Factory*").should()
            .beAnnotatedWith(Factory.class);
    @ArchTest
    public static final ArchRule ANNOTATION_DAO_RULE = classes().that().haveNameMatching(".*Dao*").should()
            .beAnnotatedWith(Dao.class);
    @ArchTest
    public static final ArchRule ANNOTATION_REPO_RULE = classes().that().haveNameMatching(".*Repo*").should()
            .beAnnotatedWith(Repo.class);
}