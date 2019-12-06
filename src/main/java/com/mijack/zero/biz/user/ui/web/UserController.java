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

import javax.annotation.Resource;

import com.mijack.zero.biz.user.ui.command.CreateUserCommand;
import com.mijack.zero.biz.user.ui.command.UpdateUserCommand;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.ui.dto.UserDto;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.ui.mapper.UserMapper;
import com.mijack.zero.biz.user.domain.usecase.UserManagerCase;
import com.mijack.zero.biz.user.domain.usecase.UserQueryCase;
import com.mijack.zero.biz.user.domain.usecase.UserCreateCase;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api")
public class UserController {
    @Resource
    private UserCreateCase userCreateCase;
    @Resource
    private UserQueryCase userQueryCase;
    @Resource
    private UserManagerCase userManagerCase;
    @Resource
    private  UserMapper userMapper;

    @PostMapping("/user/create")
    public UserDto createUser(CreateUserCommand createUserCommand) {
        return userMapper.transformDomain(userCreateCase.registerUser(createUserCommand.getName(), createUserCommand.getEmail()));
    }

    @GetMapping("/users/list")
    public List<UserDto> listUser() {
        return userMapper.transformList(userQueryCase.listUser());
    }

    @PutMapping("/user/update")
    public UserDto updateUserInfo(UpdateUserCommand updateUserCommand) {
        return userMapper.transformDomain(userManagerCase.updateUserInfo(updateUserCommand.getId(), updateUserCommand.getName(), updateUserCommand.getEmail()));
    }

    @GetMapping("/user/{id}")
    public UserDto getUserInfo(@PathVariable("id") Long userId) {
        User user = userQueryCase.getUser(userId);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        return userMapper.transformDomain(user);
    }

    @DeleteMapping("/user/{id}/delete")
    public boolean deleteUser(@PathVariable("id") Long userId) {
        return userManagerCase.deleteUser(userId);
    }
}
