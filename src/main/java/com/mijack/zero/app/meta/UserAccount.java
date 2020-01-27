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

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
@TableName("Zero_UserAccount")
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 7201792836522277833L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    Long id;
    /**
     * 用户信息
     */
    Long userId;
    /**
     * 账号类型
     */
    private Long accountTypeId;
    /**
     * 账号名称
     */
    private String title;
    /**
     * 账号编号
     */
    private String number;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
