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

package com.mijack.zero.biz.user.infrastructure.factory;

import java.sql.Timestamp;

import com.mijack.zero.biz.user.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author Mi&Jack
 */
@Component
public class UserFactory {

    /**
     * 创建用户
     *
     * @param id
     * @param name
     * @param email
     * @return
     */
    public User createUser(Long id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setDeleted(false);
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}