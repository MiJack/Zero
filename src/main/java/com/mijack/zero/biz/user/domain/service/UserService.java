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

package com.mijack.zero.biz.user.domain.service;


import com.mijack.zero.biz.user.domain.cases.UserCase;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.domain.repository.UserRepository;
import com.mijack.zero.common.exceptions.SystemErrorException;
import com.mijack.zero.common.exceptions.WrongParamException;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserRegisteredException;
import com.mijack.zero.biz.user.domain.factory.UserFactory;
import com.mijack.zero.framework.ddd.Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.mijack.zero.common.Assert;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserService implements UserCase.UserRegisterCase, UserCase.UserQueryCase, UserCase.UserManagerCase {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final UserRepository userRepository;
    final UserFactory userFactory;

    @Autowired
    public UserService(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public User registerUser(String name, String email) {
        Assert.state(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email), () -> createException(WrongParamException.class));
        Assert.isNull(userRepository.findOneByName(name), () -> createException(UserRegisteredException.class, "用户名已注册"));
        Assert.isNull(userRepository.findOneByEmail(email), () -> createException(UserRegisteredException.class, "用户邮箱已注册"));
        Long id = userRepository.allocateId();
        logger.info("allocateKey = {}", id);
        User user = userFactory.createUser(id, name, email);
        Assert.state(userRepository.updateUser(user) > 0, () -> createException("创建用户失败"));
        return user;
    }

    @Override
    public User updateUserInfo(Long id, String name, String email) {
        Assert.state(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email), () -> createException(WrongParamException.class));
        User user = userRepository.getUserById(id);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        Assert.isNull(userRepository.findOneByName(name), () -> createException(UserRegisteredException.class, "用户名已注册"));
        Assert.isNull(userRepository.findOneByEmail(email), () -> createException(UserRegisteredException.class, "用户邮箱已注册"));
        // todo 移至领域内部实现
        if (StringUtils.isNotBlank(email)) {
            user.setEmail(email);
        }
        if (StringUtils.isNotBlank(name)) {
            user.setName(name);
        }
        Assert.state(userRepository.updateUser(user) > 0, () -> createException(SystemErrorException.class, "更新数据异常"));
        return user;
    }

    @Override
    public List<User> listUser() {
        return userRepository.list();
    }

    @Override
    public boolean deleteUser(Long userId) {
        User user = userRepository.getUserById(userId);
        Assert.isNull(user, () -> createException("用户名不存在"));
        return userRepository.delete(user) > 0;
    }
}
