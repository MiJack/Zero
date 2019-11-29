/*
 *     Copyright 2019 Mi&Jack
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

package com.mijack.zero.biz.transaction.domain;

import com.mijack.zero.common.enums.IdentifiableEnum;
import lombok.Getter;

/**
 * 货币单位枚举类
 *
 * @author yuanyujie
 */
public enum Currency implements IdentifiableEnum<Currency> {
    /**
     * 人民币
     */
    CNY(1, "人民币"),
    /**
     * 美元
     */
    USD(2, "美元");
    @Getter
    private final int id;
    @Getter
    private final String desc;

    Currency(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
