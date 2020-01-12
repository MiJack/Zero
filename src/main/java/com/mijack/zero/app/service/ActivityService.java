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
import com.mijack.zero.app.command.TransactionAttachCommand;
import com.mijack.zero.common.Assert;
import com.mijack.zero.app.dao.ActivityDao;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.exception.UserNotFoundException;
import com.mijack.zero.app.meta.Activity;
import com.mijack.zero.app.meta.User;
import com.mijack.zero.app.service.user.UserService;
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
    private TransactionService transactionService;

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
        // 创建
        Activity activity = new Activity();
        activity.setMark(command.getMark());
        activity.setName(command.getName());
        activity.setUserId(command.getUserId());
        activity.setTags(command.getTags());
        activity.setActivityTime(happenTime);

        Assert.state(activityDao.insert(activity) > 0, () -> createException(BaseBizException.class, "创建活动失败"));
        Assert.state(activity.getId() > 0, () -> createException(BaseBizException.class, "创建活动失败"));

        // 创建交易
        TransactionAttachCommand transactionAttachCommand = new TransactionAttachCommand();
        transactionAttachCommand.setActivityId(activity.getId());
        transactionAttachCommand.setUserId(command.getUserId());
        transactionAttachCommand.setUserAccountId(command.getUserAccountId());
        transactionAttachCommand.setAmountMoney(command.getAmountMoney());
        transactionAttachCommand.setTransactionType(command.getTransactionType());
        transactionAttachCommand.setCreateTime(happenTime);
        Assert.state(transactionService.createTransaction(transactionAttachCommand) != null, () -> createException("创建交易失败"));

        return activity;
    }


    public Activity findActivityByActivityId(Long activityId) {
        return activityDao.selectById(activityId);
    }
}
