package com.mijack.zero.biz.account.infrastructure.dao.data;

import com.mijack.zero.framework.dao.idata.DataHolder;
import lombok.Data;

/**
 * @author yuanyujie
 */
@Data
public class UserAccountHolder implements DataHolder<UserAccountDO> {
    /**
     * 用户信息
     */
    Long userId;
    /**
     * 账号类型
     */
    private long accountType;
    /**
     * 账号名称
     */
    private String name;
}
