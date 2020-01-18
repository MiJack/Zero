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

package com.mijack.zero.biz.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import javax.annotation.Resource;

import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.User;
import com.mijack.zero.app.service.user.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mi&amp;Jack
 */
@RunWith(SpringRunner.class)
@TestConfiguration
@Ignore
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserCaseTest {
    @Resource
    UserService userService;

    String userName = "test-user";
    String userEmail = "test@test.com";
    String password = "test@test.com";

    @Test
    public void registerUser() {
        User user = userService.registerUser(userName, userEmail, password);
        assertThat(user).isNotNull().as("用户注册成功");
        User queryUser = userService.getUser(user.getId());
        assertThat(queryUser).isEqualToComparingFieldByFieldRecursively(user)
                .as("查询得到的用户信息用于注册时返回的信息保持一致");
    }

    @Test(expected = BaseBizException.class)
    public void registerUserNameAgain() {
        userService.registerUser(userName, userEmail, password);
        userService.registerUser(UUID.randomUUID().toString(), userEmail, password);
    }

    @Test(expected = BaseBizException.class)
    public void registerUserEmailAgain() {
        userService.registerUser(userName, userEmail, password);
        userService.registerUser(userName, UUID.randomUUID().toString(), password);
    }
}
