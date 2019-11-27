package com.mijack.zero.biz.financial.domain;

import lombok.Data;

/**
 * 用于表示货币单位
 *
 * @author yuanyujie
 */
@Data
public class Money {
    /**
     * 货币单位
     */
    private CurrencyUnit currencyUnit;
    /**
     * 具体数值
     */
    private Long money;
}
