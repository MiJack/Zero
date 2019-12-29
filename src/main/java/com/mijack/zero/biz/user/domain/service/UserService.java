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


import com.mijack.zero.biz.user.domain.LoginType;
import com.mijack.zero.biz.user.domain.UserAuth;
import com.mijack.zero.biz.user.domain.cases.UserCase;
import com.mijack.zero.biz.user.domain.factory.UserAuthFactory;
import com.mijack.zero.biz.user.domain.repository.UserAuthRepository;
import com.mijack.zero.biz.user.exception.UserLoginFailException;
import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.biz.user.domain.repository.UserRepository;
import com.mijack.zero.common.exceptions.SystemErrorException;
import com.mijack.zero.common.exceptions.WrongParamException;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;
import java.util.Optional;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserRegisteredException;
import com.mijack.zero.biz.user.domain.factory.UserFactory;
import com.mijack.zero.framework.ddd.Service;
import com.mijack.zero.utils.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.mijack.zero.common.Assert;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserService implements UserCase.UserRegisterCase, UserCase.UserQueryCase, UserCase.UserManagerCase, UserCase.UserLoginCase {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final UserRepository userRepository;
    final UserFactory userFactory;
    final UserAuthRepository userAuthRepository;
    final UserAuthFactory userAuthFactory;

    @Autowired
    public UserService(UserRepository userRepository, UserFactory userFactory, UserAuthRepository userAuthRepository, UserAuthFactory userAuthFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userAuthRepository = userAuthRepository;
        this.userAuthFactory = userAuthFactory;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    @Transactional()
    public User registerUser(String name, String email, String password) {
        Assert.state(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email), () -> createException(WrongParamException.class));
        Assert.isNull(userRepository.findOneByName(name), () -> createException(UserRegisteredException.class, "用户名已注册"));
        Assert.isNull(userRepository.findOneByEmail(email), () -> createException(UserRegisteredException.class, "用户邮箱已注册"));
        User user = userFactory.createUser(name, email);
        userAuthFactory.createUserAuth(user.getId(), password);
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

    @Override
    public User loginUser(int type, String email, String password) {
        LoginType loginType = EnumUtils.idOf(type, LoginType.class);
        Assert.equals(loginType, LoginType.EMAIL, () -> createException(WrongParamException.class, "不支持该登录方式"));
        User user = userRepository.findOneByEmail(email);
        Assert.notNull(user, () -> createException(UserLoginFailException.class));
        String cryptPassword = Optional.of(user).map(userAuthRepository::findOneByUser).map(UserAuth::getCryptPassword).orElse(null);
        Assert.notNull(cryptPassword, () -> createException(UserLoginFailException.class));

        Assert.equals(password, cryptPassword, () -> createException(UserLoginFailException.class));
        return user;
    }
}
