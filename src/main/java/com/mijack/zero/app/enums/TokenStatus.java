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

package com.mijack.zero.app.enums;

import com.mijack.zero.common.IBaseEnum;
import lombok.Getter;

/**
 * @author Mi&amp;Jack
 */
public enum TokenStatus implements IBaseEnum<TokenStatus> {
    /**
     * 有效
     */
    VALID(1, "有效"),
    /**
     * 过期
     */
    EXPIRE(-1, "过期"),
    /**
     * 失效
     */
    INVALID(-2, "失效的");

    @Getter
    private final int id;
    @Getter
    private final String desc;

    TokenStatus(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
