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

package com.mijack.zero.app.controller;

import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.exception.BizCode;
import com.mijack.zero.framework.web.bo.ApiResult;
import com.mijack.zero.framework.web.mvc.ApiController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/test")
public class TestController {

    @GetMapping(value = "/hello")
    public ApiResult<String> hello() {
        return ApiResult.success("Hello world");
    }

    @GetMapping(value = "/error")
    public void error() {
        throw BaseBizException.createException(BizCode.SYSTEM_ERROR);
    }

}
