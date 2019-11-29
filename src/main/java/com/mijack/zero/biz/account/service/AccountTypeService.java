/*
 *     Copyright 2019 Mi&Jack
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

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
