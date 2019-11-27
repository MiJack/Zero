package com.mijack.zero.biz.financial.dao.data;

import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yuanyujie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountBalanceDo extends AccountBalanceHolder implements IdentifiableData<Long, AccountBalanceDo>,
        DeletableDo<AccountBalanceDo> {
    private Long id;
}
