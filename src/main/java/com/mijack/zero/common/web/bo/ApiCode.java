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

import lombok.Getter;

/**
 * @author Mi&Jack
 */
public enum ApiCode {
    /**
     * 调用成功
     */
    SUCCESS(200, "调用成功");
    @Getter
    private final int code;
    @Getter
    private final String msg;

    ApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
