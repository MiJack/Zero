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

package com.mijack.zero.app.service.user;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.Optional;

import javax.annotation.Resource;

import com.mijack.zero.app.dao.UserDao;
import com.mijack.zero.app.meta.User;
import com.mijack.zero.app.meta.UserAuth;
import com.mijack.zero.app.meta.UserAuthType;
import com.mijack.zero.biz.user.domain.LoginType;
import com.mijack.zero.biz.user.exception.UserLoginFailException;
import com.mijack.zero.biz.user.exception.UserRegisteredException;
import com.mijack.zero.app.dao.UserAuthDao;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.exceptions.WrongParamException;
import com.mijack.zero.utils.EnumUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Component
public class UserService {
    @Resource
    UserDao userDao;
    @Resource
    UserAuthDao userAuthDao;

    public User registerUser(String name, String email, String password) {
        Assert.state(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email), () -> createException(WrongParamException.class));
        Assert.isNull(userDao.findOneByName(name), () -> createException(UserRegisteredException.class, "用户名已注册"));
        Assert.isNull(userDao.findOneByEmail(email), () -> createException(UserRegisteredException.class, "用户邮箱已注册"));
        // todo 邮箱合理性检查
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        userDao.insert(user);
        Long userId = user.getId();
        Assert.state(userId > 0, () -> createException("创建用户失败"));
        UserAuth userAuth = new UserAuth();
        userAuth.setId(userId);
        userAuth.setSalt("");
        userAuth.setType(UserAuthType.BASE64.getCode());
        userAuth.setCryptPassword(Base64.encodeBase64String(password.getBytes()));
        userAuthDao.insert(userAuth);
        return user;
    }

    public User loginUser(int type, String identifiableValue, String password) {
        LoginType loginType = EnumUtils.idOf(type, LoginType.class);
        Assert.equals(loginType, LoginType.EMAIL, () -> createException(WrongParamException.class, "不支持该登录方式"));
        String cryptPassword = Base64.encodeBase64String(password.getBytes());
        User user = userDao.findOneByEmail(identifiableValue);
        String cryptPasswordInDb = Optional.ofNullable(user).map(User::getId).map(userAuthDao::selectById).map(UserAuth::getCryptPassword).orElse(null);
        Assert.equals(cryptPassword, cryptPasswordInDb, () -> createException(UserLoginFailException.class));
        return user;
    }

    public User getUser(Long userId) {
        return Optional.ofNullable(userId).map(userDao::selectById).orElse(null);
    }
}
