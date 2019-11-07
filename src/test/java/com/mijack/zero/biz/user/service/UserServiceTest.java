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
import com.mijack.zero.biz.user.infrastructure.dao.UserDao;
import com.mijack.zero.biz.user.infrastructure.factory.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author Mi&Jack
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ZeroApplication.class)
//@SpringJUnitConfig(TestConfig.class)

@TestExecutionListeners({})
public class UserServiceTest {
    @Resource
    UserService userService;
    @Resource
    UserDao userDao;
    @Resource
    UserFactory userFactory;

    @Before
    public void beforeTest() {
        User user = userFactory.createUser(0L, "user", "email");
        userDao.add(user);
    }

    @Test
    public void testRegisterUser() {
        User user = userService.registerUser("test", "test");
        Assert.state(user.getEmail() == "test");
        Assert.state(user.getName() == "test");
        Assert.state(!user.isDeleted());
    }

    @Test
    public void testRegisterUserFailedCase1() {
        User user = userService.registerUser("user", "test");
    }

    @Test
    public void testRegisterUserFailedCase2() {
        User user = userService.registerUser("test", "email");
    }

    @Test
    public void testUpdateUserInfo() {
    }
}