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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mijack.zero.app.enums.AccountTypeEnums;
import com.mijack.zero.app.meta.AccountType;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class AccountTypeService {

    public List<AccountType> listAccountType() {
        return Arrays.stream(AccountTypeEnums.values())
                .map(accountTypeEnum -> {
                    AccountType accountType = new AccountType();
                    accountType.setId(accountTypeEnum.getId());
                    accountType.setName(accountTypeEnum.getName());
                    accountType.setBillingType(accountTypeEnum.getBillingType());
                    accountType.setIconId(-accountTypeEnum.getId());
                    return accountType;
                }).collect(Collectors.toList());
    }

    public AccountType findAccountTypeById(Long accountTypeId) {
        return Arrays.stream(AccountTypeEnums.values())
                .filter(accountTypeEnums -> accountTypeEnums.getId() == accountTypeId)
                .map(accountTypeEnum -> {
                    AccountType accountType = new AccountType();
                    accountType.setId(accountTypeEnum.getId());
                    accountType.setName(accountTypeEnum.getName());
                    accountType.setBillingType(accountTypeEnum.getBillingType());
                    accountType.setIconId(-accountTypeEnum.getId());
                    return accountType;
                }).findFirst().orElse(null);
    }

    public List<AccountType> findAccountTypeByIds(List<Long> accountTypeIds) {
        return Arrays.stream(AccountTypeEnums.values())
                .filter(accountTypeEnums -> accountTypeIds.contains(accountTypeEnums.getId()))
                .map(accountTypeEnum -> {
                    AccountType accountType = new AccountType();
                    accountType.setId(accountTypeEnum.getId());
                    accountType.setName(accountTypeEnum.getName());
                    accountType.setBillingType(accountTypeEnum.getBillingType());
                    accountType.setIconId(-accountTypeEnum.getId());
                    return accountType;
                }).collect(Collectors.toList());

    }
}
