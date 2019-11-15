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

package com.mijack.zero.ddd.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mi&Jack
 */
@Configuration
public class DomainRepositoryConfig {
    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor (){
        BeanFactoryPostProcessor beanFactoryPostProcessor = new BeanFactoryPostProcessor(){
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                String[] beanNames = beanFactory.getBeanNamesForType(IDomainDao.class);
            for (String beanName :beanNames){
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            }
            }
        };
        return beanFactoryPostProcessor;
    }

}
