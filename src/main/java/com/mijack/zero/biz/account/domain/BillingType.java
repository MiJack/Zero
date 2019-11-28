package com.mijack.zero.biz.account.domain;

import com.mijack.zero.common.enums.IdentifiableEnum;
import lombok.Getter;

/**
 * @author yuanyujie
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
