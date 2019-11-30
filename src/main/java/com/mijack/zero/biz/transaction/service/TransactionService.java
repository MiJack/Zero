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

package com.mijack.zero.biz.transaction.service;

import javax.annotation.Resource;

import com.mijack.zero.biz.transaction.command.TransactionAttachCommand;
import com.mijack.zero.biz.transaction.command.TransactionRemoveCommand;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.domain.Transaction;
import com.mijack.zero.biz.transaction.factory.ActivityFactory;
import com.mijack.zero.biz.transaction.factory.TransactionFactory;
import com.mijack.zero.biz.transaction.repository.TransactionRepository;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.exceptions.BaseBizException;
import com.mijack.zero.framework.ddd.Service;

import com.google.common.collect.Lists;

/**
 * @author Mi&amp;Jack
 */
@Service
public class TransactionService {
    @Resource
    TransactionRepository transactionRepository;
    @Resource
    TransactionFactory transactionFactory;
    @Resource
    ActivityFactory activityFactory;


    public Transaction attachTransaction(TransactionAttachCommand command) {
        Activity activity = activityFactory.findActivity(command.getUserId(), command.getActivityId());
        Assert.notNull(activity, () -> BaseBizException.createException("activity不存在"));
        Transaction transaction = transactionFactory.createTransaction(command);
        long count = transactionRepository.addTransaction(activity, Lists.newArrayList(transaction));
        Assert.state(count == 1, () -> BaseBizException.createException("创建事务失败"));
        return transaction;
    }

    public boolean removeTransaction(TransactionRemoveCommand command) {
        Activity activity = activityFactory.findActivity(command.getUserId(), command.getActivityId());
        Transaction transaction = transactionFactory.queryTransaction(command);
        return transactionRepository.deleteTransactions(activity, Lists.newArrayList(transaction)) == 1;
    }
}
