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

package com.mijack.zero.biz.transaction.ui.web;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.transaction.ui.command.ActivityCreateCommand;
import com.mijack.zero.biz.transaction.ui.command.ActivityDeleteCommand;
import com.mijack.zero.biz.transaction.ui.command.ActivityListCommand;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.domain.service.ActivityService;
import com.mijack.zero.app.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/activity")
public class ActivityController {
    @Resource
    ActivityService activityService;

    @RequestMapping("/list")
    public List<Activity> listActivity(ActivityListCommand activityListCommand) {
        return activityService.listActivity(activityListCommand.getUserId());
    }

    @RequestMapping("/create")
    public Activity createActivity(ActivityCreateCommand activityCreateCommand) {
        return activityService.createActivity(activityCreateCommand);
    }

    @RequestMapping("/delete")
    public boolean deleteActivity(ActivityDeleteCommand activityDeleteCommand) {
        return activityService.deleteActivity(activityDeleteCommand) > 0;
    }
}
