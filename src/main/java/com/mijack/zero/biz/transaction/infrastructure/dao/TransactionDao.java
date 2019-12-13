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

package com.mijack.zero.biz.transaction.infrastructure.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mijack.zero.biz.transaction.infrastructure.dao.data.TransactionDO;
import com.mijack.zero.framework.dao.BaseDao;
import com.mijack.zero.framework.ddd.Dao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mi&amp;Jack
 */
@Mapper
@Dao
public interface TransactionDao extends BaseDao<TransactionDO> {
    /**
     * 根据activityId查询关联的交易
     *
     * @param activityId 活动id
     * @return activity下关联的交易信息
     */
    default List<TransactionDO> listTransactionByActivityId(Long activityId) {
        return selectList(new QueryWrapper<TransactionDO>().lambda().eq(TransactionDO::getActivityId, activityId));
    }
}
