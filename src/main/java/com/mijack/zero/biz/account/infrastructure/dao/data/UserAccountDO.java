package com.mijack.zero.biz.account.infrastructure.dao.data;

import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yuanyujie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAccountDO extends UserAccountHolder implements IdentifiableData<Long, UserAccountDO>, DeletableDo<UserAccountDO> {
    private Long id;


}
