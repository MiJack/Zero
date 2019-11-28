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

package com.mijack.zero.common.dao;

import java.util.Map;

import javax.annotation.Nonnull;

import com.mijack.zero.ZeroApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Mi&amp;Jack
 */
public class ImportDaoInvokeProxyBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    public static final Logger logger = LoggerFactory.getLogger(ImportDaoInvokeProxyBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableDaoInvokeProxy.class.getName());
        logger.info("annotationAttributes = {}", annotationAttributes);
        ClassPathDaoScanner scanner = new ClassPathDaoScanner(registry);
        scanner.doScan(ZeroApplication.class.getPackage().getName());
    }
}
