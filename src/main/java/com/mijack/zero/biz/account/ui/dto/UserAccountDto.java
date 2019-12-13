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

package com.mijack.zero.biz.account.ui.dto;

import java.io.Serializable;

import com.mijack.zero.biz.user.ui.dto.UserDto;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class UserAccountDto implements Serializable {
    private static final long serialVersionUID = 1913374702024848973L;
    /**
     * 主键
     */
    Long id;
    /**
     * 用户信息
     */
    UserDto user;
    /**
     * 账号类型
     */
    private AccountTypeDto accountType;
    /**
     * 账号名称
     */
    private String name;
}
