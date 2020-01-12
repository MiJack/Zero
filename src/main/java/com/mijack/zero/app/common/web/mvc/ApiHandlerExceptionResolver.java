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

package com.mijack.zero.app.common.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.common.web.bo.ApiResult;
import com.mijack.zero.app.common.web.mvc.view.ApiJsonView;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mi&amp;Jack
 */
public class ApiHandlerExceptionResolver implements HandlerExceptionResolver {
    public static final Logger logger = LoggerFactory.getLogger(ApiHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Object handler, @NotNull Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ApiJsonView.VIEW_NAME);
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        if (ex instanceof BaseBizException) {
            code = ((BaseBizException) ex).getCode();
            message = ex.getMessage();
        }
        logger.error("url error, url = {}", request.getRequestURI(), ex);
        modelAndView.addObject(ApiJsonView.API_RESULT, ApiResult.fail(code, message));
        return modelAndView;
    }
}
