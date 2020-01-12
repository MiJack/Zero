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

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.mijack.zero.app.enums.BillingType;
import com.mijack.zero.framework.web.bo.ApiResult;
import com.mijack.zero.framework.web.mvc.ApiController;
import com.mijack.zero.utils.EnumUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api")
public class ConstantController {
    @RequestMapping("/constant/info")
    public ApiResult<Map<String, Object>> constant() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("billingType", Arrays.stream(BillingType.values()).map(EnumUtils::toEnumDto).collect(Collectors.toList()));
        return ApiResult.success(map);
    }
}
