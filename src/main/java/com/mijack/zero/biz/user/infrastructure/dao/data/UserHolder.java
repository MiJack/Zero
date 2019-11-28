/*
 *    Copyright 2019 Mi&Jack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mijack.zero.biz.user.infrastructure.dao.data;

import java.sql.Timestamp;

import com.mijack.zero.framework.dao.idata.DataHolder;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class UserHolder implements DataHolder<UserDO> {

    /**
     * 用户名
     */
    private String name;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
