/*
 *    Copyright 2019 Mi&Jack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mijack.zero.biz.account.domain.factory;

import static com.mijack.zero.app.exception.BaseBizException.createException;


import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.domain.BillingType;
import com.mijack.zero.biz.account.domain.repository.AccountTypeRepository;
import com.mijack.zero.app.common.Assert;
import com.mijack.zero.app.exception.WrongParamException;
import com.mijack.zero.framework.ddd.Factory;
import com.mijack.zero.app.utils.EnumUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mi&amp;Jack
 */
@Factory
public class AccountTypeFactory {
    final AccountTypeRepository accountTypeRepository;

    public AccountTypeFactory(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @NotNull
    public AccountType createAccountType(String typeName, String accountTypeIcon, int billingTypeCode) {
        BillingType billingType = EnumUtils.idOf(billingTypeCode, BillingType.class);
        Assert.notEmpty(typeName, () -> createException(WrongParamException.class, "typeName 为空"));
        Assert.notEmpty(accountTypeIcon, () -> createException(WrongParamException.class, "accountTypeIcon 为空"));
        Assert.notNull(billingType, () -> createException(WrongParamException.class, "billingType不存在"));

        Long id = accountTypeRepository.allocateId();
        AccountType accountType = new AccountType();
        accountType.setId(id);
        accountType.setTypeName(typeName);
        accountType.setAccountTypeIcon(accountTypeIcon);
        accountType.setBillingType(billingType);
        return accountType;
    }
}
