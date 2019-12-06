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

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.transaction.command.ActivityCreateCommand;
import com.mijack.zero.biz.transaction.command.ActivityDeleteCommand;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.factory.ActivityFactory;
import com.mijack.zero.biz.transaction.repository.ActivityRepository;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.domain.service.UserService;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.exceptions.BaseBizException;
import com.mijack.zero.framework.ddd.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class ActivityService {
    @Resource
    UserService userService;
    @Resource
    ActivityRepository activityRepository;
    @Resource
    ActivityFactory activityFactory;

    public List<Activity> listActivity(Long userId) {
        User user = userService.getUser(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        return activityRepository.listActivity(user);
    }

    public Activity createActivity(ActivityCreateCommand activityCreateCommand) {
        Activity activity = activityFactory.createActivity(activityCreateCommand);
        Assert.state(activityRepository.addActivity(activity) > 0, () -> createException(BaseBizException.class, "创建活动失败"));
        return activity;
    }

    public int deleteActivity(ActivityDeleteCommand activityDeleteCommand) {
        Activity activity = activityFactory.findActivity(activityDeleteCommand.getUserId(), activityDeleteCommand.getActivityId());
        return activityRepository.deleteActivity(activity);
    }
}
