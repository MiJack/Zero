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

package com.mijack.zero.biz.transaction.repository;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import com.mijack.zero.biz.account.repository.UserAccountRepository;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.domain.Money;
import com.mijack.zero.biz.transaction.domain.Transaction;
import com.mijack.zero.biz.transaction.domain.TransactionType;
import com.mijack.zero.biz.transaction.infrastructure.dao.TransactionDao;
import com.mijack.zero.biz.transaction.infrastructure.dao.data.TransactionDO;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.base.BaseConverter;
import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.ddd.Repo;
import com.mijack.zero.utils.EnumUtils;

import com.google.common.collect.Lists;

/**
 * @author Mi&amp;Jack
 */
@Repo
public class TransactionRepository extends BaseConverter<TransactionDO, Transaction> {
    @Resource
    private TransactionDao transactionDao;
    @Resource
    private UserAccountRepository userAccountRepository;

    public List<Transaction> listTransactionByActivity(Activity activity) {
        List<TransactionDO> db = transactionDao.listTransactionByActivityId(activity.getId());
        return db.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    protected Transaction doForward(@Nonnull TransactionDO transactionDO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDO.getId());
        transaction.setUserAccount(userAccountRepository.findUserAccountByAccountId(transactionDO.getUserAccountId()));
        transaction.setMoney(Money.create(transactionDO.getMoney(), transactionDO.getCurrency()));

        transaction.setTransactionType(EnumUtils.idOf(transactionDO.getTransactionType(), TransactionType.class));
        transaction.setCreateTime(transactionDO.getCreateTime());
        transaction.setUpdateTime(transactionDO.getUpdateTime());
        return transaction;
    }

    public long addTransaction(Activity activity, List<Transaction> transactions) {
        transactionDao.beginTransaction();
        try {
            List<TransactionDO> db = Lists.newArrayList();
            for (Transaction transaction : transactions) {
                TransactionDO transactionDO = new TransactionDO();
                transactionDO.setActivityId(activity.getId());
                transactionDO.setMoney(transaction.getMoney().getMoney());
                transactionDO.setCurrency(transaction.getMoney().getCurrency().getId());
                transactionDO.setTransactionType(transaction.getTransactionType().getId());
                transactionDO.setCreateTime(transaction.getCreateTime());
                transactionDO.setUpdateTime(transaction.getUpdateTime());
                db.add(transactionDO);
            }
            transactionDao.addDo(db);
            transactionDao.commitTransaction();
        } catch (Exception e) {
            transactionDao.rollbackTransaction();
            return 0;
        }
        return transactions.size();

    }

    public int deleteTransactions(Activity activity, List<Transaction> transactions) {
        transactionDao.beginTransaction();
        try {
            List<Long> ids = transactions.stream().map(Transaction::getId).collect(Collectors.toList());
            long deleteCount = transactionDao.delete(ids, Criteria.eq("activityId", activity.getId()));
            Assert.state(deleteCount == ids.size(), () -> createException("删除事务失败"));
            transactionDao.commitTransaction();
            return transactions.size();
        } catch (Exception e) {
            transactionDao.rollbackTransaction();
            return 0;
        }
    }

    public Transaction findTransactionById(Long transactionId) {
        return convert(transactionDao.getById(transactionId));
    }
}
