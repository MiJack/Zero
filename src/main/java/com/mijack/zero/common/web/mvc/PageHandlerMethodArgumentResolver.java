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

package com.mijack.zero.common.web.mvc;

import javax.servlet.http.HttpServletRequest;

import com.mijack.zero.common.exceptions.ControllerException;
import com.mijack.zero.common.exceptions.SystemErrorException;
import com.mijack.zero.common.web.bo.QueryPage;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 处理分页请求参数
 *
 * @author Mi&amp;Jack
 */
public class PageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LIMIT_KEY = "limit";
    public static final String OFFSET_KEY = "offset";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(QueryPage.class);
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            throw new SystemErrorException();
        }
        String offset = servletRequest.getParameter(OFFSET_KEY);
        String limit = servletRequest.getParameter(LIMIT_KEY);
        if (limit == null || offset == null) {
            throw new ControllerException(400, "分页请求缺少分页参数");
        }
        QueryPage requestQueryPage = new QueryPage();
        requestQueryPage.setLimit(Long.parseLong(limit));
        requestQueryPage.setOffset(Long.parseLong(offset));
        return requestQueryPage;
    }
}
