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

package com.mijack.zero.common.mybatis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @author Mi&Jack
 */
public class ClassPathDaoScanner extends ClassPathBeanDefinitionScanner  {
    public static final Logger logger = LoggerFactory.getLogger(ClassPathDaoScanner.class);

    public ClassPathDaoScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        addIncludeFilter(new AnnotationTypeFilter(Repository.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        GenericBeanDefinition definition;

        for (BeanDefinitionHolder holder : beanDefinitions) {

            try {
                definition = (GenericBeanDefinition) holder.getBeanDefinition();
                String beanClass = definition.getBeanClassName();
                if (StringUtils.isEmpty(beanClass)) {
                    continue;
                }
                definition.setBeanClass(DaoFactory.class);
                definition.getConstructorArgumentValues().addIndexedArgumentValue(0, ClassUtils.forName(beanClass, DaoFactory.class.getClassLoader()));
                logger.info("accept info: beanDefinitionHolder = {}", holder.getBeanName());
            } catch (ClassNotFoundException e) {
                logger.error("doScan error: ", e);
            }
        }
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

}
