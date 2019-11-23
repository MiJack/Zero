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

package com.mijack.zero.biz.account.service;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.factory.UserAccountFactory;
import com.mijack.zero.biz.account.factory.AccountTypeFactory;
import com.mijack.zero.biz.account.repository.UserAccountRepository;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.service.UserService;
import com.mijack.zero.common.Assert;
import org.springframework.stereotype.Service;

/**
 * @author Mi&Jack
 */
@Service
public class UserAccountService {

    @Resource
    UserAccountRepository userAccountRepository;
    @Resource
    UserService userService;
    @Resource
    AccountTypeFactory accountTypeFactory;
    @Resource
    UserAccountFactory userAccountFactory;

    public List<UserAccount> listUserAccount(long userId) {
        User user = userService.getUser(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        return userAccountRepository.listUserAccount(userId);
    }

    public UserAccount createAccount(long userId, String accountName, Long accountTypeCode) {

        UserAccount userAccount = userAccountFactory.create(userId, accountName, accountTypeCode);
        userAccountRepository.addUserAccount(userAccount);
        return userAccount;
    }

    public UserAccount deleteAccount(long userId, long accountId) {

        //TODO: to write the method deleteAccount
        throw new UnsupportedOperationException();
    }
}
