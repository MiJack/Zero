package com.mijack.zero.common.dao;

import java.util.Map;

import com.mijack.zero.ZeroApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yuanyujie
 */
public class ImportDaoInvokeProxyBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    public static final Logger logger = LoggerFactory.getLogger(ImportDaoInvokeProxyBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableDaoInvokeProxy.class.getName());
        logger.info("annotationAttributes = {}", annotationAttributes);
        ClassPathDaoScanner scanner = new ClassPathDaoScanner(registry);
        scanner.doScan(ZeroApplication.class.getPackage().getName());
    }
}
