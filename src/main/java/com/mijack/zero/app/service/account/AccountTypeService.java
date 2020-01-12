/*
 *     Copyright 2020 Mi&Jack
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

package com.mijack.zero.app.service.account;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mijack.zero.common.Assert;
import com.mijack.zero.app.dao.AccountTypeDao;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.AccountType;
import com.mijack.zero.utils.EnumUtils;
import com.mijack.zero.app.enums.BillingType;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class AccountTypeService {
    @Resource
    AccountTypeDao accountTypeDao;

    public AccountType createAccountType(String typeName, String accountTypeIcon, int billingType) {
        AccountType accountType = new AccountType();
        accountType.setBillingType(EnumUtils.idOf(billingType, BillingType.class));
        accountType.setAccountTypeIcon(accountTypeIcon);
        accountType.setTypeName(typeName);
        accountTypeDao.insert(accountType);
        Assert.state(accountType.getId() > 0, () -> BaseBizException.createException("创建账户类型失败"));
        return accountType;
    }

    public List<AccountType> listAccountType() {
        return accountTypeDao.selectList(new QueryWrapper<AccountType>().lambda());
    }
}
