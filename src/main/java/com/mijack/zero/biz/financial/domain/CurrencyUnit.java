package com.mijack.zero.biz.financial.domain;

import com.mijack.zero.common.enums.IdentifiableEnum;
import lombok.Getter;

/**
 * 货币单位枚举类
 *
 * @author yuanyujie
 */
public enum CurrencyUnit implements IdentifiableEnum<CurrencyUnit> {
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

    CurrencyUnit(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
