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

package com.mijack.zero.biz.account.domain;

import com.mijack.zero.app.common.enums.IdentifiableEnum;
import lombok.Getter;

/**
 * @author Mi&amp;Jack
 */
public enum BillingType implements IdentifiableEnum<BillingType> {
    /**
     * 余额类型
     */
    BALANCE(1),
    /**
     * 理财型
     */
    FINANCIAL(2),
    /**
     * 理财余额类型
     */
    FINANCIAL_BALANCE(3),
    /**
     * 预支型
     */
    PREPAYMENT(4);
    @Getter
    private final int id;

    BillingType(int id) {
        this.id = id;
    }
}
