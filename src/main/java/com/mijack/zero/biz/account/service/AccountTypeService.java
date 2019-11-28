package com.mijack.zero.biz.account.service;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.factory.AccountTypeFactory;
import com.mijack.zero.biz.account.repository.AccountTypeRepository;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.exceptions.BaseBizException;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class AccountTypeService {
    final AccountTypeRepository accountTypeRepository;
    final AccountTypeFactory accountTypeFactory;

    public AccountTypeService(AccountTypeRepository accountTypeRepository, AccountTypeFactory accountTypeFactory) {
        this.accountTypeRepository = accountTypeRepository;
        this.accountTypeFactory = accountTypeFactory;
    }

    public AccountType createAccountType(String typeName, String accountTypeIcon, int billingType) {
        AccountType accountType = accountTypeFactory.createAccountType(typeName, accountTypeIcon, billingType);
        long count = accountTypeRepository.addAccountType(accountType);
        Assert.equals(count, 1, () -> createException(BaseBizException.class, "AccountType创建失败"));
        return accountType;
    }

    public List<AccountType> listAccountType() {
        return accountTypeRepository.listAccountType();
    }
}
