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

package com.mijack.zero.biz.transaction.factory;

import java.sql.Timestamp;

import javax.annotation.Resource;

import com.mijack.zero.biz.transaction.command.ActivityCreateCommand;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.repository.ActivityRepository;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.infrastructure.repository.UserRepository;
import com.mijack.zero.framework.ddd.Factory;
import com.mijack.zero.utils.CollectionHelper;

import com.google.common.collect.Lists;

/**
 * @author Mi&amp;Jack
 */
@Factory
public class ActivityFactory {
    @Resource
    UserRepository userRepository;
    @Resource
    TransactionFactory transactionFactory;
    @Resource
    ActivityRepository activityRepository;

    public Activity createActivity(ActivityCreateCommand command) {
        Timestamp now = command.getCreateTime() != null ? command.getCreateTime() :
                new Timestamp(System.currentTimeMillis());
        Activity activity = new Activity();
        activity.setMark(command.getMark());
        activity.setName(command.getName());
        activity.setUser(userRepository.getUserById(command.getUserId()));
        activity.setTags(CollectionHelper.toList(command.getTags().split(",")));
        activity.setTransactions(Lists.newArrayList(transactionFactory.createTransaction(command)));
        activity.setCreateTime(now);
        return activity;
    }

    public Activity findActivity(Long userId, Long activityId) {
        User user = userRepository.getUserById(userId);
        return activityRepository.findActivityByUserAndActivityId(user, activityId);
    }
}
