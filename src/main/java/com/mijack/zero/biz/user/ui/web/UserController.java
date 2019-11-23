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

package com.mijack.zero.biz.user.ui.web;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import com.mijack.zero.biz.user.command.CreateUserCommand;
import com.mijack.zero.biz.user.command.UpdateUserCommand;
import com.mijack.zero.biz.user.dao.UserDO;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.service.UserService;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author Mi&Jack
 */
@ApiController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/")
    public User createUser(CreateUserCommand createUserCommand) {
        return userService.registerUser(createUserCommand.getName(), createUserCommand.getEmail());
    }

    @GetMapping("/users")
    public List<UserDO> listUser() {
        return userService.listUser();
    }

    @PutMapping("/user")
    public User updateUserInfo(UpdateUserCommand updateUserCommand) {
        return userService.updateUserInfo(updateUserCommand.getId(), updateUserCommand.getName(), updateUserCommand.getEmail());
    }

    @GetMapping("/user/{id}")
    public User getUserInfo(@PathVariable("id") Long userId) {
        User user = userService.getUser(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        return user;
    }
}
