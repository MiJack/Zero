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

package com.mijack.zero.config;

import javax.sql.DataSource;

import com.mijack.zero.common.dao.CompositeCriteriaSqlFormatter;
import com.mijack.zero.common.dao.DaoInvokeProxyConfiguration;
import com.mijack.zero.common.dao.EnableDaoInvokeProxy;
import com.mijack.zero.common.dao.ForCriteria;
import com.mijack.zero.framework.dao.Criteria;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Mi&Jack
 */
@Configuration
@EnableDaoInvokeProxy
public class SpringJdbcConfig {
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ZeroDB");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    DaoInvokeProxyConfiguration daoInvokeHandlerConfiguration(JdbcTemplate jdbcTemplate, ApplicationContext applicationContext) {
        DaoInvokeProxyConfiguration daoInvokeHandlerConfiguration = new DaoInvokeProxyConfiguration();
        daoInvokeHandlerConfiguration.setJdbcTemplate(jdbcTemplate);


        CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter = new CompositeCriteriaSqlFormatter();
        String[] criteriaFormatterNames = applicationContext.getBeanNamesForType(CompositeCriteriaSqlFormatter.CriteriaFormatter.class);
        for (String criteriaFormatterName : criteriaFormatterNames) {
            @SuppressWarnings("unchecked")
            CompositeCriteriaSqlFormatter.CriteriaFormatter<? extends Criteria> criteriaFormatter = applicationContext.getBean(criteriaFormatterName, CompositeCriteriaSqlFormatter.CriteriaFormatter.class);
            ForCriteria forCriteria = criteriaFormatter.getClass().getAnnotation(ForCriteria.class);
            if (forCriteria != null) {
                for (Class<? extends Criteria> clazz : forCriteria.clazzes()) {
                    compositeCriteriaSqlFormatter.registerCriteriaFormatter(clazz, criteriaFormatter);
                }
            } else {
                // todo logger worn
            }
        }

        daoInvokeHandlerConfiguration.setCompositeCriteriaSqlFormatter(compositeCriteriaSqlFormatter);
        return daoInvokeHandlerConfiguration;
    }
}