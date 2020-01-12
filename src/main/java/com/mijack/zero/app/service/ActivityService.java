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


import static com.mijack.zero.app.exception.BaseBizException.createException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.mijack.zero.app.command.ActivityCreateCommand;
import com.mijack.zero.app.common.Assert;
import com.mijack.zero.app.dao.ActivityDao;
import com.mijack.zero.app.dao.TransactionDao;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.exception.UserNotFoundException;
import com.mijack.zero.app.meta.Activity;
import com.mijack.zero.app.meta.MoneyFactory;
import com.mijack.zero.app.meta.Transaction;
import com.mijack.zero.app.meta.User;
import com.mijack.zero.app.meta.UserAccount;
import com.mijack.zero.app.meta.constant.TransactionType;
import com.mijack.zero.app.service.user.UserService;
import com.mijack.zero.app.utils.EnumUtils;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private UserService userService;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private MoneyFactory moneyFactory;
    @Resource
    private TransactionDao transactionDao;

    public List<Activity> listActivity(Long userId) {
        User user = userService.getUser(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        return activityDao.selectActivityByUserId(userId);
    }

    public Activity createActivity(ActivityCreateCommand command) {
        User user = userService.getUser(command.getUserId());
        Assert.state(user != null, () -> createException("用户不存在"));
        Timestamp happenTime = Optional.ofNullable(command.getHappenTime())
                .orElseGet(() -> new Timestamp(System.currentTimeMillis()));
        // 创建交易
        Transaction transaction = createTransaction(command);
        Assert.state(transactionDao.insert(transaction) > 0, () -> createException("创建交易失败"));

        // 创建
        Activity activity = new Activity();
        activity.setMark(command.getMark());
        activity.setName(command.getName());
        activity.setUserId(command.getUserId());
        activity.setTags(command.getTags());
        activity.setHappenTime(happenTime);

        Assert.state(activityDao.insert(activity) > 0, () -> createException(BaseBizException.class, "创建活动失败"));
        return activity;
    }


    public Transaction createTransaction(ActivityCreateCommand command) {
        Timestamp now = command.getHappenTime() != null ? command.getHappenTime() :
                new Timestamp(System.currentTimeMillis());
        UserAccount userAccount = userAccountService.findUserAccountById(command.getUserAccountId());
        Assert.state(userAccount != null, () -> createException("用户账号不存在"));
        Transaction transaction = new Transaction();
        transaction.setMoney(moneyFactory.parse(command.getMoney()));
        transaction.setTransactionType(EnumUtils.idOf(command.getTransactionType(), TransactionType.class));
        transaction.setUserAccountId(command.getUserAccountId());
        transaction.setUpdateTime(now);
        return transaction;
    }

    public Activity findActivityByActivityId(Long activityId) {
        return activityDao.selectById(activityId);
    }
}
