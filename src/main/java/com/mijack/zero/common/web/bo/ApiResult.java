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

package com.mijack.zero.common.web.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * http api接口统一返回的结果格式
 *
 * @author Mi&Jack
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = -8953859682856579697L;
    /**
     * 响应码，200为正常
     */
    private int code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 返回结果
     */
    private T data;

    public static <T> ApiResult<T> build(T data, int code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        return build(data, ApiCode.SUCCESS);
    }

    public static <T> ApiResult<T> build(T data, ApiCode code) {
        return build(data, code.getCode(), code.getMsg());
    }
}
