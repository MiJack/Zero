/*
 *     Copyright 2020 Mi&Jack
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

package com.mijack.zero.app.controller;


import static com.mijack.zero.app.exception.BaseBizException.createException;

import java.util.Map;

import javax.annotation.Resource;

import com.mijack.zero.app.exception.UserError;
import com.mijack.zero.framework.context.UserContext;
import com.mijack.zero.app.meta.ApiToken;
import com.mijack.zero.app.meta.User;
import com.mijack.zero.app.service.user.UserService;
import com.mijack.zero.app.service.user.UserTokenService;
import com.mijack.zero.app.command.CreateUserCommand;
import com.mijack.zero.app.command.UserLoginCommand;
import com.mijack.zero.common.Assert;
import com.mijack.zero.framework.web.bo.ApiResult;
import com.mijack.zero.framework.web.mvc.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    UserTokenService userTokenService;

    @PostMapping("/user/register")
    public User createUser(CreateUserCommand createUserCommand) {
        return userService.registerUser(createUserCommand.getName(), createUserCommand.getEmail(), createUserCommand.getPassword());
    }

    @PostMapping("/user/login")
    @ResponseBody
    public ApiResult<Map<String, Object>> loginUser(UserLoginCommand userLoginCommand) {
        User user = userService.loginUser(userLoginCommand.getLoginType(), userLoginCommand.getIdentifiableValue(), userLoginCommand.getPassword());
        ApiToken apiToken = userTokenService.generationUserApiToken(user.getId());
        return ApiResult.success(new ImmutableMap.Builder<String, Object>()
                .put("apiToken", apiToken)
                .put("user", user)
                .build());
    }

    @GetMapping("/user/info")
    public User getLoginUserInfo() {
        Long userId = UserContext.getCurrentUserId();
        return getUserInfo(userId);
    }

    @GetMapping("/user/{id}")
    public User getUserInfo(@PathVariable("id") Long userId) {
        User user = userService.getUser(userId);
        Assert.notNull(user, () -> createException(UserError.USER_NOT_FOUND));
        return user;
    }
}
