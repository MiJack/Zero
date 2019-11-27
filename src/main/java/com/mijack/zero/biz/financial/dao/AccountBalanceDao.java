package com.mijack.zero.biz.financial.dao;

import com.mijack.zero.biz.financial.dao.data.AccountBalanceDo;
import com.mijack.zero.framework.dao.idao.BasicDao;
import org.springframework.stereotype.Repository;

/**
 * @author yuanyujie
 */
@Repository
public interface AccountBalanceDao extends BasicDao<Long, AccountBalanceDo> {
}
