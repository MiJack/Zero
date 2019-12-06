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

package com.mijack.zero.biz.transaction.ui.command;

import java.sql.Timestamp;

import com.mijack.zero.biz.transaction.domain.TransactionType;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class TransactionAttachCommand {

    /**
     * 产生事项的用户
     */
    private Long userId;
    private Long activityId;
    /**
     * 对应账户的资金的变动
     */
    private Long userAccountId;
    /**
     * 对应的格式为CNY 111.22
     */
    private String money;
    /**
     * @see TransactionType#getId()
     */
    private Integer transactionType;
    /**
     * 事项最早一笔资金产生的时间
     */
    private Timestamp createTime;
}
