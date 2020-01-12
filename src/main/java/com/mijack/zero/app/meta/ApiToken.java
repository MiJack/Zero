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
import com.mijack.zero.app.enums.TokenStatus;
import com.mijack.zero.app.enums.TokenType;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
@TableName("Zero_Token")
public class ApiToken implements Serializable {
    private static final long serialVersionUID = -2617788946172473353L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * token 类型
     */
    private TokenType type;
    /**
     * 资源类型
     */
    private Long resourceId;
    /**
     * token内容
     */
    private String token;
    /**
     * token过期时间
     */
    private Timestamp expire;
    /**
     * token状态
     */
    private TokenStatus tokenStatus;

    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

}
