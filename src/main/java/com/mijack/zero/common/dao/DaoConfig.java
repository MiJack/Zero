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

package com.mijack.zero.common.dao;

import java.lang.reflect.InvocationHandler;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.IDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import com.mijack.zero.common.dao.CompositeCriteriaSqlFormatter.CriteriaFormatter;

/**
 * @author Mi&Jack
 */
@Configuration
@Import(ClassPathDaoScanner.class)
public class DaoConfig {
    @Bean
    DaoInvokeConfiguration daoInvokeHandlerConfiguration(JdbcTemplate jdbcTemplate, ApplicationContext applicationContext) {
        DaoInvokeConfiguration daoInvokeHandlerConfiguration = new DaoInvokeConfiguration();
        daoInvokeHandlerConfiguration.setJdbcTemplate(jdbcTemplate);


        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = new CompositeCriteriaSqlFormatter();
        String[] criteriaFormatterNames = applicationContext.getBeanNamesForType(CriteriaFormatter.class);
        for (String criteriaFormatterName : criteriaFormatterNames) {
            @SuppressWarnings("unchecked")
            CriteriaFormatter<? extends Criteria> criteriaFormatter = applicationContext.getBean(criteriaFormatterName, CriteriaFormatter.class);
            ForCriteria forCriteria = criteriaFormatter.getClass().getAnnotation(ForCriteria.class);
            if (forCriteria != null) {
                for (Class<? extends Criteria> clazz : forCriteria.clazzes()) {
                    compositeCriteriaSqlFormatter.registerCriteriaFormatter(clazz, criteriaFormatter);
                }
            } else {
                // todo logger worn
            }
        }


        String[] daoNames = applicationContext.getBeanNamesForType(IDao.class);
        for (String daoName : daoNames) {

            IDao dao = applicationContext.getBean(daoName, IDao.class);
            if (dao instanceof InvocationHandler) {
                // skip
            } else {
                Class<?>[] interfaces = dao.getClass().getInterfaces();
                for (Class<?> interfaceClazz : interfaces) {
                    if (interfaceClazz.isAssignableFrom(IDao.class)) {
                        @SuppressWarnings("unchecked")
                        Class<? extends IDao<?>> daoClazz = (Class<? extends IDao<?>>) interfaceClazz;
                        daoInvokeHandlerConfiguration.registerDao(daoClazz, dao);
                    }
                }
            }

        }


        daoInvokeHandlerConfiguration.setCompositeCriteriaSqlFormatter(compositeCriteriaSqlFormatter);
        return daoInvokeHandlerConfiguration;
    }
}
