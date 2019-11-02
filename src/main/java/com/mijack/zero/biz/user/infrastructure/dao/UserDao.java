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

import java.util.List;

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
     * 列举用户
     *
     * @param offset
     * @param limit
     * @return
     */
    List<User> listUser(long offset, long limit);

    /**
     * 根据用户名查找对应的用户
     *
     * @param name 用户名
     * @return 如果未查询到，返回null
     */
    User findOneByName(String name);

    /**
     * 根据邮箱名查找对应的用户
     *
     * @param email 邮箱名
     * @return 如果未查询到，返回null
     */
    User findOneByEmail(String email);
}
