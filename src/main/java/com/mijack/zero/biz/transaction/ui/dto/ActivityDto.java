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
import java.util.List;

import com.mijack.zero.biz.transaction.domain.Transaction;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.ui.dto.UserDto;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class ActivityDto implements Serializable {
    private static final long serialVersionUID = -2174455068133348614L;
    /**
     * 事项id
     */
    private Long id;
    /**
     * 产生事项的用户
     */
    private UserDto user;
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
    private List<String> tags;
    /**
     * 对应账户的资金的变动
     */
    private List<TransactionDto> transactions;
    /**
     * 事项最早一笔资金产生的时间
     */
    private Timestamp createTime;
    /**
     * 事项最新一笔资金产生的时间
     */
    private Timestamp updateTime;
}
