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

package com.mijack.zero.app.service;

import java.sql.Timestamp;

import javax.annotation.Resource;

import com.mijack.zero.app.command.TransactionAttachCommand;
import com.mijack.zero.app.command.TransactionRemoveCommand;
import com.mijack.zero.app.component.Money;
import com.mijack.zero.app.service.account.UserAccountService;
import com.mijack.zero.common.Assert;
import com.mijack.zero.app.dao.TransactionDao;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.Activity;
import com.mijack.zero.app.component.MoneyFactory;
import com.mijack.zero.app.meta.Transaction;
import com.mijack.zero.app.meta.UserAccount;
import com.mijack.zero.app.enums.TransactionType;
import com.mijack.zero.common.EnumUtils;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class TransactionService {
    @Resource
    ActivityService activityService;
    @Resource
    UserAccountService userAccountService;
    @Resource
    MoneyFactory moneyFactory;
    @Resource
    TransactionDao transactionDao;

    public Transaction attachTransaction(TransactionAttachCommand command) {
        Activity activity = activityService.findActivityByActivityId(command.getActivityId());
        Assert.equals(activity.getUserId(), command.getUserId(), () -> BaseBizException.createException("用户下无该活动"));
        Assert.notNull(activity, () -> BaseBizException.createException("activity不存在"));
        Transaction transaction = createTransaction(command);
        Assert.state(transactionDao.insert(transaction) > 0, () -> BaseBizException.createException("创建事务失败"));
        return transaction;
    }


    public boolean removeTransaction(TransactionRemoveCommand command) {
        Activity activity = activityService.findActivityByActivityId(command.getActivityId());
        Assert.equals(activity.getUserId(), command.getUserId(), () -> BaseBizException.createException("用户下无该活动"));
        Transaction transaction = transactionDao.selectById(command.getTransactionId());
        Assert.notNull(transaction, () -> BaseBizException.createException("用户下无该活动交易"));
        return transactionDao.deleteById(command.getTransactionId()) > 0;
    }


    public Transaction createTransaction(TransactionAttachCommand command) {
        Timestamp now = command.getCreateTime() != null ? command.getCreateTime() :
                new Timestamp(System.currentTimeMillis());
        UserAccount userAccount = userAccountService.findUserAccountById(command.getUserAccountId());
        Assert.state(userAccount != null, () -> BaseBizException.createException("用户账号不存在"));
        Transaction transaction = new Transaction();
        transaction.setActivityId(command.getActivityId());
        transaction.setTransactionType(EnumUtils.idOf(command.getTransactionType(), TransactionType.class));
        Money money = moneyFactory.parse(command.getAmountMoney());
        transaction.setCurrency(money.getCurrency());
        transaction.setAmount(money.getMoney());
        transaction.setUserAccountId(command.getUserAccountId());
        transaction.setUpdateTime(now);
        return transaction;
    }

}
