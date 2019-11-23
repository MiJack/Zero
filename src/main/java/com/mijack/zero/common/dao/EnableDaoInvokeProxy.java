package com.mijack.zero.common.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @author yuanyujie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ImportDaoInvokeProxyBeanDefinitionRegistrar.class)
public @interface EnableDaoInvokeProxy {
}
