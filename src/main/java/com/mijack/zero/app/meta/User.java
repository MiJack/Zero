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
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mijack.zero.app.common.Assert;
import com.mijack.zero.app.exception.BaseBizException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mi&amp;Jack
 */
@Data
@NoArgsConstructor
@TableName("Zero_User")
@AllArgsConstructor
public class User {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@(\\w+.)+\\w+");
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
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

    public void setEmail(String email) {
        Assert.state(EMAIL_PATTERN.matcher(email).matches(), () -> BaseBizException.createException("邮箱格式错误"));
        this.email = email;
    }
}
