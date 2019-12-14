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

package com.mijack.zero.common.web.mvc;

import java.beans.PropertyDescriptor;
import java.util.List;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mijack.zero.common.exceptions.SystemErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Mi&amp;Jack
 */
public class CommandMethodArgumentResolver implements HandlerMethodArgumentResolver {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Command.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            throw new SystemErrorException();
        }
        Class<?> commandType = parameter.getParameterType();
        return objectMapper.readValue(servletRequest.getInputStream(), commandType);
//        Object command = BeanUtils.instantiateClass(commandType);
//        BeanWrapper beanWrapper = new BeanWrapperImpl(command);
//        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
//        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//            if (propertyDescriptor.getWriteMethod() != null && propertyDescriptor.getReadMethod() != null) {
//                String propertyDescriptorName = propertyDescriptor.getName();
//                beanWrapper.setPropertyValue(propertyDescriptorName, servletRequest.getParameter(propertyDescriptorName));
//            }
//        }
//        return command;
    }

}
