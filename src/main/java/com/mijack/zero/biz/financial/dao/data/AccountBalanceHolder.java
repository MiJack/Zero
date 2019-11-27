package com.mijack.zero.biz.financial.dao.data;

import com.mijack.zero.biz.financial.domain.CurrencyUnit;
import com.mijack.zero.framework.dao.idata.DataHolder;
import lombok.Data;

/**
 * @author yuanyujie
 */
@Data
public class AccountBalanceHolder implements DataHolder<AccountBalanceDo> {
    /**
     * 关联账号的id
     */
    private Long accountId;
    /**
     * 货币单位
     *
     * @see CurrencyUnit#getId()
     */
    private Long currencyUnit;
    /**
     * 当前资产
     */
    private Long balance;
    /**
     * 清算点id
     */
    private Long checkpointId;
}
