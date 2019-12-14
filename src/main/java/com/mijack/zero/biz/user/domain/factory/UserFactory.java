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

package com.mijack.zero.biz.user.domain.factory;

import java.sql.Timestamp;

import javax.annotation.Resource;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.domain.repository.UserRepository;
import com.mijack.zero.framework.ddd.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mi&amp;Jack
 */
@Factory
public class UserFactory {
    public static final Logger logger = LoggerFactory.getLogger(UserFactory.class);
    @Resource
    private UserRepository userRepository;

    /**
     * 创建用户
     *
     * @param name  用户名
     * @param email 用户邮箱
     * @return 创建的用户
     */
    public User createUser(String name, String email) {
        Long id = userRepository.allocateId();
        logger.info("allocateKey = {}", id);
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}
