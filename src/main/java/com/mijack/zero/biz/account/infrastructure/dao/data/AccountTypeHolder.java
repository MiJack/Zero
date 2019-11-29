package com.mijack.zero.biz.account.infrastructure.dao.data;

import com.mijack.zero.biz.account.domain.BillingType;
import com.mijack.zero.framework.dao.idata.DataHolder;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class AccountTypeHolder implements DataHolder<AccountTypeDO> {
    /**
     * 账号类型名称
     */
    private String typeName;
    /**
     * 账号类型图标
     */
    private String accountTypeIcon;
    /**
     * 类型
     */
    private BillingType billingType;
}
