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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.infrastructure.dao.UserAccountDao;
import com.mijack.zero.biz.account.infrastructure.dao.data.UserAccountDO;
import com.mijack.zero.biz.user.infrastructure.repository.UserRepository;
import com.mijack.zero.common.base.BaseConverter;
import com.mijack.zero.framework.ddd.Repo;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mi&amp;Jack
 */
@Repo
public class UserAccountRepository extends BaseConverter<UserAccountDO, UserAccount> {
    final UserRepository userRepository;
    final UserAccountDao userAccountDao;
    final AccountTypeRepository accountTypeRepository;

    public UserAccountRepository(UserRepository userRepository, UserAccountDao userAccountDao, AccountTypeRepository accountTypeRepository) {
        this.userRepository = userRepository;
        this.userAccountDao = userAccountDao;
        this.accountTypeRepository = accountTypeRepository;
    }

    public List<UserAccount> listUserAccount(long userId) {
        @NotNull List<UserAccountDO> accountsInDb = userAccountDao.listUserAccount(userId);
        List<UserAccount> userAccounts = new ArrayList<>();
        for (UserAccountDO userAccountDO : accountsInDb) {
            userAccounts.add(convert(userAccountDO));
        }
        return userAccounts;
    }

    public long addUserAccount(@NotNull UserAccount userAccount) {
        UserAccountDO convert = Objects.requireNonNull(reverse().convert(userAccount));
        return convert.getId() == null ? userAccountDao.insert(convert) : userAccountDao.updateById(convert);
    }

    @Override
    protected UserAccountDO doBackward(@org.jetbrains.annotations.NotNull UserAccount userAccount) {
        UserAccountDO userAccountDO = new UserAccountDO();
        userAccountDO.setId(userAccount.getId());
        userAccountDO.setAccountType(userAccount.getAccountType().getId());
        userAccountDO.setName(userAccount.getName());
        userAccountDO.setUserId(userAccount.getUser().getId());
        return userAccountDO;
    }

    @Override
    protected UserAccount doForward(@org.jetbrains.annotations.NotNull UserAccountDO userAccountDO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(userAccountDO.getName());
        userAccount.setId(userAccountDO.getId());
        userAccount.setUser(userRepository.getUserById(userAccountDO.getUserId()));
        userAccount.setAccountType(accountTypeRepository.getAccountTypeById(userAccountDO.getAccountType()));
        return userAccount;
    }

    public UserAccount findUserAccount(long userId, long accountId) {
        UserAccountDO userAccountDO = userAccountDao.findByUserIdAndAccountId(userId, accountId);
        return convert(userAccountDO);
    }

    public boolean deleteAccount(UserAccount userAccount) {
        return userAccountDao.deleteByUserIdAndAccountId(userAccount.getId(), userAccount.getUser().getId()) > 0;
    }

    public UserAccount findUserAccountByAccountId(Long userAccountId) {
        return convert(userAccountDao.selectById(userAccountId));
    }
}
