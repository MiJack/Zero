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

package com.mijack.zero.app.exception;

/**
 * @author Mi&amp;Jack
 */
public interface IError {
    /**
     * 异常代码
     *
     * @return 异常代码
     */
    int getCode();

    /**
     * 异常信息
     *
     * @return 异常信息
     */
    String getMessage();

    static BaseBizException createBaseBizException(IError error) {
        return new BaseBizException(error.getCode(), error.getMessage());
    }
}
