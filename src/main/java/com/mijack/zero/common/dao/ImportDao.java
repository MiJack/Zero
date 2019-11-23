package com.mijack.zero.common.dao;

import com.mijack.zero.ZeroApplication;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yuanyujie
 */
public class ImportDao implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathDaoScanner scanner = new ClassPathDaoScanner(registry);
        scanner.doScan(ZeroApplication.class.getPackage().getName());
    }
}
