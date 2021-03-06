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
 * 货币单位枚举类
 *
 * @author yuanyujie
 */
public enum Currency implements IBaseEnum<Currency> {
    /**
     * 人民币
     */
    CNY(1, "CNY", "人民币", 0.01f),
    /**
     * 美元
     */
    USD(2, "USD", "美元", 0.01f),
    /**
     * 日元
     */
    JPY(3, "JPY", "日元", 1f);
    @Getter
    private final int id;
    @Getter
    private final String shortName;
    @Getter
    private final String desc;
    /**
     * 货币辅助单元，即最小计费单元
     */
    @Getter
    private final float minor;

    Currency(int id, String shortName, String desc, float minor) {
        this.id = id;
        this.shortName = shortName;
        this.desc = desc;
        this.minor = minor;
    }
}
