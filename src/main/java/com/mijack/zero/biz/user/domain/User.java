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

package com.mijack.zero.biz.user.domain;

import java.sql.Timestamp;

import com.mijack.zero.ddd.domain.DeletableDomain;
import com.mijack.zero.ddd.infrastructure.FieldNameMapper;
import com.mijack.zero.ddd.infrastructure.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户信息抽象
 *
 * @author Mi&Jack
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements DeletableDomain<Long> {
    private static final long serialVersionUID = 8983720612047767458L;
    /**
     * 用户id
     */
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
    /**
     * 是否删除
     */
    private boolean deleted;

}
