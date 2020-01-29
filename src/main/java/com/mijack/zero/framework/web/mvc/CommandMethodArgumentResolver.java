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

package com.mijack.zero.framework.web.mvc;


import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.exception.BizCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Mi&amp;Jack
 */
public class CommandMethodArgumentResolver implements HandlerMethodArgumentResolver {
    ObjectMapper objectMapper = new ObjectMapper();
    public static final Logger logger = LoggerFactory.getLogger(CommandMethodArgumentResolver.class);

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Command.class.isAssignableFrom(parameter.getParameterType()) || parameter.getParameterType().getSimpleName().endsWith("Command");
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            throw BaseBizException.createException(BizCode.SYSTEM_ERROR);
        }
        Class<?> commandType = parameter.getParameterType();
        try {
            return objectMapper.readValue(servletRequest.getInputStream(), commandType);
        } catch (Exception e) {
            logger.error("resolveArgument error: parameter = {}, mavContainer = {}, nativeWebRequest = {}, binderFactory = {}", parameter, mavContainer, nativeWebRequest, binderFactory, e);
            return BeanUtils.instantiateClass(commandType);
        }
    }

}
