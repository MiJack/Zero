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

package com.mijack.zero.biz.account.infrastructure.dao;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.mijack.zero.biz.account.infrastructure.dao.data.UserAccountDO;
import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.ddd.Dao;

/**
 * @author Mi&amp;Jack
 */
@Dao
public interface UserAccountDao extends BasicDao<Long, UserAccountDO> {
    /**
     * 列举一个用户下的所有账号
     *
     * @param userId
     * @return
     */
    default @NotNull List<UserAccountDO> listUserAccount(Long userId) {
        return query(Criteria.eq("userId", userId));
    }

    /**
     * 给定userId和accountId，返回对应的UserAccount
     *
     * @param userId
     * @param accountId
     * @return
     */
    default UserAccountDO findByUserIdAndAccountId(long userId, long accountId) {
        return findOne(Criteria.eq("id", accountId).and(Criteria.eq("userId", userId)));
    }

    /**
     * 给定userId和accountId，删除对应的UserAccount
     *
     * @param userId
     * @param accountId
     * @return
     */
    default long deleteByUserIdAndAccountId(Long userId, Long accountId) {
        return delete(Criteria.eq("id", accountId).and(Criteria.eq("userId", userId)));
    }
}
