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

package com.mijack.zero.biz.account.domain.service;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.domain.factory.UserAccountFactory;
import com.mijack.zero.biz.account.domain.repository.UserAccountRepository;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.domain.service.UserDomainService;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.exceptions.BaseBizException;
import com.mijack.zero.framework.ddd.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserAccountService {

    @Resource
    UserAccountRepository userAccountRepository;
    @Resource
    UserDomainService userDomainService;
    @Resource
    UserAccountFactory userAccountFactory;

    public List<UserAccount> listUserAccount(long userId) {
        User user = userDomainService.getUser(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        return userAccountRepository.listUserAccount(userId);
    }

    public UserAccount createAccount(long userId, String accountName, Long accountTypeCode) {
        UserAccount userAccount = userAccountFactory.create(userId, accountName, accountTypeCode);
        Assert.state(userAccountRepository.addUserAccount(userAccount) > 0, () -> createException(BaseBizException.class, "创建用户账号失败"));
        return userAccount;
    }

    public UserAccount deleteAccount(long userId, long accountId) {
        UserAccount userAccount = userAccountRepository.findUserAccount(userId, accountId);
        Assert.state(userAccountRepository.deleteAccount(userAccount), () -> createException("用户删除失败"));
        return userAccount;
    }
}
