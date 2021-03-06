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

package com.mijack.zero.framework.web.mvc;

import static com.mijack.zero.framework.web.bo.ApiResult.*;

import com.mijack.zero.framework.web.bo.ApiResult;
import com.mijack.zero.framework.web.mvc.view.ApiJsonView;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.common.collect.Maps;

/**
 * @author Mi&amp;Jack
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiResponseReturnValueHandler implements HandlerMethodReturnValueHandler {

    /**
     * 如果方法或者方法是在类有ApiResponse注解，支持
     *
     * @param returnType 返回类型
     * @return 是否支持
     * @see ApiResponse
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ApiResponse.class) ||
                returnType.hasMethodAnnotation(ApiResponse.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, @NotNull MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  @NotNull NativeWebRequest webRequest) {
        ApiResult<?> result = toApiResult(returnValue);
        mavContainer.setViewName(ApiJsonView.VIEW_NAME);
        mavContainer.addAttribute(ApiJsonView.API_RESULT, result);
    }

    private ApiResult<?> toApiResult(Object returnValue) {
        if (returnValue == null) {
            return success(Maps.newHashMap());
        }
        if (returnValue instanceof ApiResult) {
            return (ApiResult<?>) returnValue;
        } else {
            return success(returnValue);
        }
    }

}
