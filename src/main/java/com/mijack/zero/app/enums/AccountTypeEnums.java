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

import lombok.Getter;

/**
 * @author Mi&amp;Jack
 */
public enum AccountTypeEnums {
    /**
     * 支付宝（余额）
     */
    ALIPAY(1L, "支付宝（余额）", "imgs/account/alipay.png", BillingType.BALANCE),
    /**
     * 微信支付
     */
    WEPAY(2L, "微信支付", "imgs/account/wepay.png", BillingType.BALANCE),
    /**
     * 中国招商银行借记卡
     */
    CMB_DEBIT_CARD(3L, "中国招商银行（借记卡）", "imgs/account/cmb.png", BillingType.BALANCE),
    /**
     * 中国招商银行信用卡
     */
    CMB_CREDIT_CARD(4L, "中国招商银行（信用卡）", "imgs/account/cmb.png", BillingType.PREPAYMENT),
    /**
     * 杭州银行（借记卡）
     */
    HANGZHOU_BANK_DEBIT_CARD(5L, "杭州银行（借记卡）", "imgs/account/hangzhou_bank.png", BillingType.BALANCE),
    /**
     * 花呗
     */
    HUAPAY(6L, "花呗", "imgs/account/huapay.png", BillingType.PREPAYMENT);
    @Getter
    private final long id;
    @Getter
    private final String name;
    @Getter
    private final String imagePath;
    @Getter
    private final BillingType billingType;

    AccountTypeEnums(long id, String name, String imagePath, BillingType billingType) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.billingType = billingType;
    }
}
