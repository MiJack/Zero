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


import java.util.List;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.app.common.Money;
import com.mijack.zero.biz.transaction.domain.Transaction;
import lombok.Data;

/**
 * 账号清算点
 *
 * @author Mi&amp;Jack
 */
@Data
public class CheckPoint {

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
     *
     */
    private List<Transaction> transactions;
    /**
     * 如果
     */
    private CheckPoint lastCheckpoint;
}
