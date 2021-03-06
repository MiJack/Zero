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

package com.mijack.zero.app.command;

import java.sql.Timestamp;

import com.mijack.zero.app.enums.TransactionType;
import lombok.Data;

/**
 * 目前支持单个资金变动
 *
 * @author Mi&amp;Jack
 */
@Data
public class ActivityCreateCommand {

    /**
     * 产生事项的用户
     */
    private Long userId;
    /**
     * 事项的名称
     */
    private String name;
    /**
     * 事项的备注
     */
    private String mark;
    /**
     * 标记
     */
    private String tags;
    /**
     * 对应账户的资金的变动
     */
    private Long userAccountId;
    /**
     * 对应的格式为CNY 111.22
     */
    private String amountMoney;
    /**
     * @see TransactionType#getId()
     */
    private Integer transactionType;
    /**
     * 事项最早一笔资金产生的时间
     */
    private Timestamp happenTime;
}
