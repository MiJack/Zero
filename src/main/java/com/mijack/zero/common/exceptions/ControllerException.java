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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * web层的异常
 *
 * @author Mi&amp;Jack
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = 1300702019559492578L;
    /**
     * 请求返回的响应码
     */
    private final int code;
    /**
     * 请求的响应信息
     */
    private final String msg;

    public ControllerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
