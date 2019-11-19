///*
// *    Copyright 2019 Mi&Jack
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.mijack.zero.common.mybatis;
//
//import java.lang.reflect.Method;
//
//import com.mijack.zero.ddd.domain.BaseDomain;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.SqlSource;
//import org.apache.ibatis.session.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author Mi&Jack
// */
//public class UpdateDomainSqlSource implements SqlSource {
//    public static final Logger logger = LoggerFactory.getLogger(UpdateDomainSqlSource.class);
//    private final Configuration configuration;
//    private final String tableName;
//    private final Class<? extends BaseDomain<?>> domainClazz;
//    private final Method method;
//
//    public UpdateDomainSqlSource(Configuration configuration, String tableName, Class<? extends BaseDomain<?>> domainClazz, Method method) {
//        this.configuration = configuration;
//        this.tableName = tableName;
//        this.domainClazz = domainClazz;
//        this.method = method;
//    }
//
//    @Override
//    public BoundSql getBoundSql(Object parameterObject) {
//        logger.info("UpdateDomainSqlSource info: configuration = {}, tableName = {}, domainClazz = {}, method = {}", configuration, tableName, domainClazz, method);
//        return null;
//    }
//}
