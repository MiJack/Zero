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

import javax.annotation.Resource;

import com.mijack.zero.biz.user.config.UserConfig;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.domain.usecase.UserCreateCase;
import com.mijack.zero.biz.user.domain.usecase.UserQueryCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Mi&amp;Jack
 */
@RunWith(SpringRunner.class)
@TestConfiguration
@Ignore
@SpringBootTest(classes = {UserConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserCaseTest {
    @Resource
    UserCreateCase userCreateCase;
    @Resource
    UserQueryCase userQueryCase;

    @Test()
    public void registerUser() {
        String userName = "test-user";
        String userEmail = "test@test.com";
        User user = userCreateCase.registerUser(userName, userEmail);
        assertThat(user).isNotNull().as("用户注册成功");
        User queryUser = userQueryCase.getUser(user.getId());
        assertThat(queryUser).isEqualToComparingFieldByFieldRecursively(user)
                .as("查询得到的用户信息用于注册时返回的信息保持一致");
    }
}
