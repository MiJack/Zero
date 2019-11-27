package com.mijack.zero.biz.financial.domain;

import com.mijack.zero.biz.account.domain.UserAccount;
import lombok.Data;

/**
 * @author yuanyujie
 */
@Data
public class AccountBalance {
    /**
     * id
     */
    private Long id;
    /**
     * 关联账号的id
     */
    private UserAccount userAccount;
    /**
     * 当前资产
     */
    private Money balance;
    /**
     * 清算点id
     */
    private CheckPoint checkpoint;
}
