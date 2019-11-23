package com.mijack.zero.biz.account.service;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.factory.AccountTypeFactory;
import com.mijack.zero.biz.account.infrastructure.dao.AccountTypeDao;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.exceptions.BaseBizException;
import org.springframework.stereotype.Service;

/**
 * @author yuanyujie
 */
@Service
public class AccountTypeService {
    final AccountTypeDao accountTypeDao;
    final AccountTypeFactory accountTypeFactory;

    public AccountTypeService(AccountTypeDao accountTypeDao, AccountTypeFactory accountTypeFactory) {
        this.accountTypeDao = accountTypeDao;
        this.accountTypeFactory = accountTypeFactory;
    }

    public AccountType createAccountType(String typeName, String accountTypeIcon, int billingType) {
        AccountType accountType = accountTypeFactory.createAccountType(typeName, accountTypeIcon, billingType);
        long count = accountTypeDao.addAccountType(accountType);
        Assert.equals(count, 1, () -> createException(BaseBizException.class, "AccountType创建失败"));
        return accountType;
    }
}
