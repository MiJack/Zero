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

import com.mijack.zero.common.Assert;
import com.mijack.zero.app.dao.AccountTypeDao;
import com.mijack.zero.app.dao.UserAccountDao;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.UserAccount;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserAccountService {
    @Resource
    UserAccountDao userAccountDao;
    @Resource
    AccountTypeDao accountTypeDao;

    public List<UserAccount> listUserAccount(long userId) {
        return userAccountDao.selectByUserAccount(userId);
    }

    public UserAccount createAccount(long userId, String accountName, Long accountType) {
        Assert.notNull(accountTypeDao.selectById(accountType), () -> BaseBizException.createException("账户类型不存在"));
        UserAccount userAccount = new UserAccount();
        userAccount.setTitle(accountName);
        userAccount.setUserId(userId);
        userAccount.setAccountTypeId(accountType);
        Assert.state(userAccountDao.insert(userAccount) > 0, () -> BaseBizException.createException("创建账户失败"));
        return userAccount;
    }

    public UserAccount deleteAccount(long userId, long accountId) {
        UserAccount userAccount = userAccountDao.selectById(accountId);
        Assert.notNull(userAccount, () -> BaseBizException.createException("账户不存在"));
        Assert.equals(userAccount.getUserId(), userId, () -> BaseBizException.createException("账户不存在"));
        Assert.state(userAccountDao.deleteById(accountId) > 0, () -> BaseBizException.createException("删除账号失败"));
        return userAccount;
    }

    public UserAccount findUserAccountById(Long userAccountId) {
        return userAccountDao.selectById(userAccountId);
    }
}
