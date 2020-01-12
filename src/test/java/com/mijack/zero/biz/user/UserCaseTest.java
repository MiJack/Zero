///*
// *     Copyright 2019 Mi&Jack
// *
// *     Licensed under the Apache License, Version 2.0 (the "License");
// *     you may not use this file except in compliance with the License.
// *     You may obtain a copy of the License at
// *
// *         http://www.apache.org/licenses/LICENSE-2.0
// *
// *     Unless required by applicable law or agreed to in writing, software
// *     distributed under the License is distributed on an "AS IS" BASIS,
// *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *     See the License for the specific language governing permissions and
// *     limitations under the License.
// */
//
//package com.mijack.zero.biz.user;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import java.util.UUID;
//
//import javax.annotation.Resource;
//
//import com.mijack.zero.biz.user.domain.User;
//import com.mijack.zero.biz.user.domain.cases.UserCase;
//import com.mijack.zero.app.exception.UserRegisteredException;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author Mi&amp;Jack
// */
//@RunWith(SpringRunner.class)
//@TestConfiguration
//@Ignore
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//public class UserCaseTest {
//    @Resource
//    UserCase.UserRegisterCase userRegisterCase;
//    @Resource
//    UserCase.UserQueryCase userQueryCase;
//
//    String userName = "test-user";
//    String userEmail = "test@test.com";
//    String password = "test@test.com";
//
//    @Test
//    public void registerUser() {
//        User user = userRegisterCase.registerUser(userName, userEmail, password);
//        assertThat(user).isNotNull().as("用户注册成功");
//        User queryUser = userQueryCase.getUser(user.getId());
//        assertThat(queryUser).isEqualToComparingFieldByFieldRecursively(user)
//                .as("查询得到的用户信息用于注册时返回的信息保持一致");
//    }
//
//    @Test
//    public void registerUsers() {
//        int count = 10;
//        for (int i = 0; i < count; i++) {
//            String name = i + userName;
//            String password = i + userEmail;
//            userRegisterCase.registerUser(name, password, password);
//        }
//        List<User> users = userQueryCase.listUser();
//        assertThat(users).hasSize(count).as("列表数目应和注册数目保持一致");
//    }
//
//    @Test(expected = UserRegisteredException.class)
//    public void registerUserNameAgain() {
//        userRegisterCase.registerUser(userName, userEmail, password);
//        userRegisterCase.registerUser(UUID.randomUUID().toString(), userEmail, password);
//    }
//
//    @Test(expected = UserRegisteredException.class)
//    public void registerUserEmailAgain() {
//        userRegisterCase.registerUser(userName, userEmail, password);
//        userRegisterCase.registerUser(userName, UUID.randomUUID().toString(), password);
//    }
//}
