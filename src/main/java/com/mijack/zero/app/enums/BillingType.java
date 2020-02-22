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
public enum BillingType implements IBaseEnum<BillingType> {
    /**
     * 余额类型
     */
    BALANCE(1, "余额类型"),
    /**
     * 理财型
     */
    FINANCIAL(2, "理财型"),
    /**
     * 理财余额类型
     */
    FINANCIAL_BALANCE(BALANCE.getId() + FINANCIAL.getId(), "理财余额类型"),
    /**
     * 预支型
     */
    PREPAYMENT(4, "预支型");
    @Getter
    private final int id;
    @Getter
    private final String desc;

    BillingType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
