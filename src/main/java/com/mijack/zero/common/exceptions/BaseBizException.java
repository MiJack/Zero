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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * 针对业务的异常
 *
 * @author Mi&Jack
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class BaseBizException extends RuntimeException {
    private static final long serialVersionUID = -8684647774757420813L;
    /**
     * 异常代码
     */
    private final int code;
    /**
     * 异常信息
     */
    private String message;

    public BaseBizException() {
        this(BizCode.SYSTEM_ERROR);
    }

    public BaseBizException(IError bizCode) {
        this.code = bizCode.getCode();
        this.message = bizCode.getMessage();
    }

    public static <T extends BaseBizException> T createException(Class<T> clazz) {
        return BeanUtils.instantiateClass(clazz);
    }

    public static <T extends BaseBizException> T createException(Class<T> clazz, String message) {
        T exception = createException(clazz);
        exception.setMessage(message);
        return exception;
    }
}
