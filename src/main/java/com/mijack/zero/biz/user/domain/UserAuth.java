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

package com.mijack.zero.biz.user.domain;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class UserAuth {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 密码对应的盐
     */
    private String salt;
    /**
     * 加密类型
     */
    private int type;
    /**
     * 加密后的密码
     */
    private String cryptPassword;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
