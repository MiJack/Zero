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

package com.mijack.zero.biz.financial.domain;

import com.mijack.zero.biz.account.domain.UserAccount;
import lombok.Data;

/**
 * {@code UserAccount }和{@code AccountBalance}的关系的是1对多的关系
 *
 * @author Mi&amp;Jack
 */
@Data
public class AccountBalance {
    /**
     * id
     */
    private Long id;
    /**
     * 关联账号的id
     */
    private UserAccount userAccount;
    /**
     * 当前资产
     */
    private Money balance;
    /**
     * 清算点id
     */
    private CheckPoint checkpoint;
}
