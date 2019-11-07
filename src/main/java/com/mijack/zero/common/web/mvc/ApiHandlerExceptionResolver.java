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
import javax.servlet.http.HttpServletResponse;

import com.mijack.zero.common.exceptions.BaseBizException;
import com.mijack.zero.common.web.bo.ApiResult;
import com.mijack.zero.common.web.mvc.view.ApiJsonView;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mi&Jack
 */
public class ApiHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // todo handler为什么是一个object？？
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ApiJsonView.VIEW_NAME);
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        if (ex instanceof BaseBizException) {
            code = ((BaseBizException) ex).getCode();
            message = ex.getMessage();
        }
        modelAndView.addObject(ApiJsonView.API_RESULT, ApiResult.fail(code, message));
        return modelAndView;
    }
}
