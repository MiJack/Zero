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

package com.mijack.zero.biz.user.dao;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.BasicDao;
import org.springframework.stereotype.Repository;

/**
 * @author Mi&Jack
 */
@Repository
public interface IUserDao extends BasicDao<Long, UserDO> {

    /**
     * 根据用户名查找对应的用户
     *
     * @param name 用户名
     * @return 如果未查询到，返回null
     */
    default UserDO findOneByName(String name) {
        return findOne(Criteria.eq("name", name));
    }

    /**
     * 根据邮箱名查找对应的用户
     *
     * @param email 邮箱名
     * @return 如果未查询到，返回null
     */
    default UserDO findOneByEmail(String email) {
        return findOne(Criteria.eq("email", email));
    }

}
