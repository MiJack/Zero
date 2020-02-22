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

package com.mijack.zero.app.meta;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 资金活动的事项
 *
 * @author Mi&amp;Jack
 */
@Data
@TableName("Zero_UserActivity")
public class Activity {
    /**
     * 事项id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 产生事项的用户
     */
    private Long userId;
    /**
     * 事项的名称
     */
    private String name;
    /**
     * 事项的备注
     */
    private String mark;
    /**
     * 标签
     */
    private String tags;
    /**
     * 事项产生的时间
     */
    private Timestamp activityTime;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
