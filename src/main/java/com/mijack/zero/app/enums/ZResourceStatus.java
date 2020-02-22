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

import com.mijack.zero.common.IdentifiableEnum;
import lombok.Getter;

/**
 * @author Mi&amp;Jack
 */
public enum ZResourceStatus implements IdentifiableEnum<ZResourceStatus> {
    /**
     * 初始化
     */
    INIT(0, "初始化"),
    /**
     * 初始化
     */
    UPLOADING(1, "初始化"),
    /**
     * 校验中
     */
    CHECKING(2, "校验中"),
    /**
     * 已完成
     */
    OK(2, "已完成"),
    /**
     * 失效
     */
    INVALID(-1, "失效");
    @Getter
    private final int id;
    @Getter
    private final String desc;

    ZResourceStatus(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

}
