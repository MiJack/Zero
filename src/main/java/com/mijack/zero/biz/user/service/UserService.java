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


import com.mijack.zero.biz.user.exception.UserNotFoundException;
import com.mijack.zero.common.exceptions.SystemErrorException;
import com.mijack.zero.common.exceptions.WrongParamException;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.exception.UserRegisteredException;
import com.mijack.zero.biz.user.infrastructure.dao.UserDao;
import com.mijack.zero.biz.user.infrastructure.factory.UserFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mijack.zero.common.Assert;

/**
 * @author Mi&Jack
 */
@Service
public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final UserDao userDao;
    final  UserFactory userFactory;

    @Autowired
    public UserService(UserDao userDao, UserFactory userFactory) {
        this.userDao = userDao;
        this.userFactory = userFactory;
    }

    public User getUser(long userId) {
        return userDao.findOne(userId);
    }

    public User registerUser(String name, String email) {
        Assert.state(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email), () -> createException(WrongParamException.class));
        Assert.isNull(userDao.findOneByName(name), () -> createException(UserRegisteredException.class, "用户名已注册"));
        Assert.isNull(userDao.findOneByEmail(email), () -> createException(UserRegisteredException.class, "用户邮箱已注册"));
        Long id = userDao.allocateKey();
        logger.info("allocateKey = {}", id);
        User user = userFactory.createUser(id, name, email);
        userDao.add(user);
        return user;
    }

    public User updateUserInfo(Long id, String name, String email) {
        Assert.state(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email), () -> createException(WrongParamException.class));
        User user = userDao.findOne(id);
        Assert.notNull(user, () -> createException(UserNotFoundException.class));
        Assert.isNull(userDao.findOneByName(name), () -> createException(UserRegisteredException.class, "用户名已注册"));
        Assert.isNull(userDao.findOneByEmail(email), () -> createException(UserRegisteredException.class, "用户邮箱已注册"));
        // todo 移至领域内部实现
        if (StringUtils.isNotBlank(email)) {
            user.setEmail(email);
        }
        if (StringUtils.isNotBlank(name)) {
            user.setName(name);
        }
        Assert.state(userDao.update(user) > 0, () -> createException(SystemErrorException.class, "更新数据异常"));
        return user;
    }

    public List<User> listUser() {
        return userDao.listAll();
    }
}
