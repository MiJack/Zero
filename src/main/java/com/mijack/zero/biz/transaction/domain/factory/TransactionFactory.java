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

package com.mijack.zero.biz.transaction.domain.factory;

import java.sql.Timestamp;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.domain.factory.MoneyFactory;
import com.mijack.zero.biz.account.domain.repository.UserAccountRepository;
import com.mijack.zero.biz.transaction.ui.command.ActivityCreateCommand;
import com.mijack.zero.biz.transaction.ui.command.TransactionAttachCommand;
import com.mijack.zero.biz.transaction.ui.command.TransactionRemoveCommand;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.domain.Transaction;
import com.mijack.zero.app.common.TransactionType;
import com.mijack.zero.biz.transaction.domain.repository.ActivityRepository;
import com.mijack.zero.biz.transaction.domain.repository.TransactionRepository;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.domain.repository.UserRepository;
import com.mijack.zero.app.common.Assert;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.framework.ddd.Factory;
import com.mijack.zero.app.utils.EnumUtils;

/**
 * @author Mi&amp;Jack
 */
@Factory
public class TransactionFactory {
    @Resource
    UserAccountRepository userAccountRepository;
    @Resource
    TransactionRepository transactionRepository;
    @Resource
    ActivityRepository activityRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    MoneyFactory moneyFactory;

    public Transaction createTransaction(ActivityCreateCommand command) {
        Timestamp now = command.getCreateTime() != null ? command.getCreateTime() :
                new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction();
        transaction.setMoney(moneyFactory.parse(command.getMoney()));
        transaction.setTransactionType(EnumUtils.idOf(command.getTransactionType(), TransactionType.class));
        transaction.setUserAccount(userAccountRepository.findUserAccountByAccountId(command.getUserAccountId()));
        transaction.setUpdateTime(now);
        return transaction;
    }

    public Transaction createTransaction(TransactionAttachCommand command) {
        Timestamp now = command.getCreateTime() != null ? command.getCreateTime() :
                new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction();
        transaction.setTransactionType(EnumUtils.idOf(command.getTransactionType(), TransactionType.class));
        transaction.setMoney(moneyFactory.parse(command.getMoney()));
        transaction.setUserAccount(userAccountRepository.findUserAccountByAccountId(command.getUserAccountId()));
        transaction.setUpdateTime(now);
        return transaction;
    }

    public Transaction queryTransaction(TransactionRemoveCommand command) {
        User user = userRepository.getUserById(command.getUserId());
        Assert.notNull(user, () -> BaseBizException.createException("用户未找到"));
        UserAccount userAccount = userAccountRepository.findUserAccount(command.getUserId(), command.getUserAccountId());
        Assert.notNull(userAccount, () -> BaseBizException.createException("用户账号未找到"));
        Activity activity = activityRepository.findActivityByUserAndActivityId(user, command.getActivityId());
        Assert.notNull(activity, () -> BaseBizException.createException("活动未找到"));
        return transactionRepository.findTransactionById(command.getTransactionId());
    }
}