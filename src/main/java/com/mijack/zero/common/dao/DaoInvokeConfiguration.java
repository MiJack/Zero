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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mijack.zero.framework.dao.idao.IDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

/**
 * @author Mi&Jack
 */
public class DaoInvokeConfiguration implements BeanPostProcessor {
    @Getter
    @Setter
    private JdbcTemplate jdbcTemplate;
    @Getter
    @Setter
    private CompositeCriteriaSqlFormatter compositeCriteriaSqlFormatter;
    private final Map<Class<? extends IDao<?>>, IDao<?>> daoMap = new HashMap<>();

    public void registerDao(Class<? extends IDao<?>> interfaceClazz, IDao dao) {
        daoMap.put(interfaceClazz, dao);
    }

    public Map<Class<? extends IDao<?>>, IDao<?>> getDaoMap() {
        return Collections.unmodifiableMap(daoMap);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (!(bean instanceof IDao)) {
            return null;
        }
        IDao<?> dao = (IDao<?>) bean;
        if (dao instanceof InvocationHandler) {
            // skip
            return null;
        }
        @SuppressWarnings("unchecked")
        Class<? extends IDao<?>> daoClazz = (Class<? extends IDao<?>>) dao.getClass();
        registerDao(daoClazz, dao);
        return null;
    }
}
