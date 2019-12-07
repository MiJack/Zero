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

package com.mijack.zero.biz.transaction.ui.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.mijack.zero.biz.account.ui.dto.UserAccountDto;
import com.mijack.zero.biz.common.Money;
import com.mijack.zero.biz.common.TransactionType;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class TransactionDto implements Serializable {
    private static final long serialVersionUID = -8868897945331263033L;

    private Long id;
    private UserAccountDto userAccount;
    private Money money;
    private TransactionType transactionType;
    private Timestamp createTime;
    private Timestamp updateTime;
}
