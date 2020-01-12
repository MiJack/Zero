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

package com.mijack.zero.app.exception;

import lombok.Getter;

/**
 * @author Mi&amp;Jack
 */
public enum UserError implements IError {
    /**
     * 用户信息已注册
     */
    USER_REGISTERED(-100, "用户信息已注册"),
    /**
     * 用户未找到
     */
    USER_NOT_FOUND(-200, "用户未找到"),
    /**
     * 账号密码错误
     */
    LOGIN_FAIL(-300, "账号密码错误");
    @Getter
    private final int code;
    @Getter
    private final String message;

    UserError(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
