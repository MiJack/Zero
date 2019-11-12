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

package com.mijack.zero.biz.user.infrastructure.dao;

import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import com.mijack.zero.ddd.infrastructure.IDomainDao;
import com.mijack.zero.biz.user.domain.User;
import org.springframework.stereotype.Repository;

/**
 * 用户存储管理
 *
 * @author Mi&Jack
 */
@Repository
public interface UserDao extends IDomainDao<Long, User> {

    /**
     * 根据用户名查找对应的用户
     *
     * @param name 用户名
     * @return 如果未查询到，返回null
     */
    default User findOneByName(String name) {
        return findOne(Criteria.eq("name", name));
    }

    /**
     * 根据邮箱名查找对应的用户
     *
     * @param email 邮箱名
     * @return 如果未查询到，返回null
     */
    default User findOneByEmail(String email){
        return findOne(Criteria.eq("email", email));
    }
}
