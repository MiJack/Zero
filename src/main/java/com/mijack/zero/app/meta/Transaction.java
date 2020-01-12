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

import com.mijack.zero.app.meta.constant.TransactionType;
import lombok.Data;

/**
 * 交易，往往会引起多个账户的余额变动，这里只关注余额的变动，不关心具体的数值<br/>
 * 例如
 * <ol>
 *     <li>购买商品</li>
 *     <li>股票收益</li>
 *     <li>分期付款</li>
 *     <li>转账</li>
 * </ol>
 *
 * @author Mi&amp;Jack
 */
@Data
public class Transaction {
    private Long id;
    private Long activityId;
    private Long userAccountId;
    private Money money;
    private TransactionType transactionType;
    private Timestamp createTime;
    private Timestamp updateTime;
}
