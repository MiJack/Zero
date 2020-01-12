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

import javax.annotation.Resource;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.domain.repository.AccountTypeRepository;
import com.mijack.zero.biz.account.infrastructure.dao.UserAccountDao;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.app.exception.UserNotFoundException;
import com.mijack.zero.biz.user.domain.repository.UserRepository;
import com.mijack.zero.app.common.Assert;
import com.mijack.zero.framework.ddd.Factory;

/**
 * @author Mi&amp;Jack
 */
@Factory
public class UserAccountFactory {
    @Resource
    UserAccountDao userAccountDao;
    @Resource
    UserRepository userRepository;
    @Resource
    AccountTypeRepository accountTypeRepository;

    public UserAccount create(long userId, String accountName, Long accountTypeCode) {
        User user = userRepository.getUserById(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        AccountType accountType = accountTypeRepository.getAccountTypeById(accountTypeCode);
        Long id = userAccountDao.allocateId();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        userAccount.setUser(user);
        userAccount.setName(accountName);
        userAccount.setAccountType(accountType);
        return userAccount;
    }
}
