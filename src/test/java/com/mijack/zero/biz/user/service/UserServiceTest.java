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

package com.mijack.zero.biz.user.service;


import javax.annotation.Resource;

import com.mijack.zero.ZeroApplication;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserRegisteredException;
import com.mijack.zero.biz.user.infrastructure.dao.UserDao;
import com.mijack.zero.biz.user.infrastructure.factory.UserFactory;
import com.mijack.zero.ddd.domain.IDomainKeyGenerator;
import com.mijack.zero.ddd.domain.MemoryDomainDaoFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @author Mi&Jack
 */
@SpringBootTest
@Import(ZeroApplication.class)
public class UserServiceTest {
    @Resource
    UserService userService;
    @Resource
    UserDao userDao;
    @Resource
    UserFactory userFactory;
    private final IDomainKeyGenerator<Long, User> domainKeyGenerator = map -> (long) (map.values().size() + 1);

    @Before
    public void beforeTest() {
        userFactory = new UserFactory();
        userDao = MemoryDomainDaoFactory.proxyForDao(UserDao.class, domainKeyGenerator);
        userService = new UserService(userDao, userFactory);

        User user = userFactory.createUser(0L, "user", "email");
        userDao.add(user);
    }

    @Test
    public void testRegisterUser() {
        User user = userService.registerUser("test", "test");
        assertEquals("参数错误", "test", user.getEmail());
        assertEquals("参数错误", "test", user.getName());
        assertFalse("参数错误", user.isDeleted());
    }

    @Test(expected = UserRegisteredException.class)
    public void testRegisterUserFailedCase1() {
        userService.registerUser("user", "test");
    }

    @Test(expected = UserRegisteredException.class)
    public void testRegisterUserFailedCase2() {
        userService.registerUser("test", "email");
    }

    @Test
    public void testUpdateUserInfo() {
        userService.updateUserInfo(0L, "", "");
    }
}