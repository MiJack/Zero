/*
 *     Copyright 2020 Mi&Jack
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

package com.mijack.zero.framework.config;

import java.util.List;

import com.mijack.zero.framework.web.mvc.ApiHandlerExceptionResolver;
import com.mijack.zero.framework.web.mvc.ApiResponseReturnValueHandler;
import com.mijack.zero.framework.web.mvc.CommandMethodArgumentResolver;
import com.mijack.zero.framework.web.mvc.PageHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

/**
 * @author Mi&amp;Jack
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean(name = "localUriBuilderFactory")
    public DefaultUriBuilderFactory localUriBuilderFactory(@Value("${application.storage.server-host}") String storageServerHost) {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(storageServerHost);
        return defaultUriBuilderFactory;
    }

    @Bean
    public ApiResponseReturnValueHandler apiResponseReturnValueHandler() {
        return new ApiResponseReturnValueHandler();
    }

    @Bean
    public ApiHandlerExceptionResolver apiHandlerExceptionResolver() {
        return new ApiHandlerExceptionResolver();
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(apiResponseReturnValueHandler());
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(apiHandlerExceptionResolver());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageHandlerMethodArgumentResolver());
        argumentResolvers.add(new CommandMethodArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
