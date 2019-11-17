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

package com.mijack.zero.common.exceptions;

import lombok.Getter;

/**
 * @author Mi&Jack
 */
public enum BizCode implements IError {
    /**
     * 调用成功
     */
    SUCCESS(200, "调用成功"),
    /**
     * 参数异常
     */
    WRONG_PARAM(400, "参数异常"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),
    /**
     * 序列化失败
     */
    SERIALIZABLE_FAIL(500,"序列化失败");
    @Getter
    private int code;
    @Getter
    private String message;

    BizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
