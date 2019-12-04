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

package com.mijack.zero.biz.account.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.infrastructure.dao.AccountTypeDao;
import com.mijack.zero.biz.account.infrastructure.dao.data.AccountTypeDO;
import com.mijack.zero.common.base.BaseConverter;
import com.mijack.zero.framework.ddd.Repo;

/**
 * @author Mi&amp;Jack
 */
@Repo
public class AccountTypeRepository extends BaseConverter<AccountTypeDO, AccountType> {
    @Resource
    AccountTypeDao accountTypeDao;

    /**
     * 添加AccountType
     *
     * @param accountType
     * @return
     */
    public long addAccountType(AccountType accountType) {
        AccountTypeDO accountTypeDO = reverse().convert(accountType);
        return accountTypeDao.insert(accountTypeDO);
    }

    /**
     * 给定id，返回对应的AccountType
     *
     * @param id
     * @return
     */
    public AccountType getAccountTypeById(Long id) {
        return Optional.ofNullable(accountTypeDao.selectById(id)).map(this::convert).orElse(null);
    }

    /**
     * 列举所有的AccountType
     *
     * @return
     */
    public List<AccountType> listAccountType() {
        List<AccountTypeDO> result = accountTypeDao.selectList(null);
        return result.stream().map(this::convert).collect(Collectors.toList());
    }

    public Long allocateId() {
        return accountTypeDao.allocateId();
    }
}
