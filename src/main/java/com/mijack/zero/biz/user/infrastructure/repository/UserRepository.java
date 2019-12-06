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

package com.mijack.zero.biz.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.infrastructure.dao.UserDao;
import com.mijack.zero.biz.user.infrastructure.dao.data.UserDO;
import com.mijack.zero.biz.user.infrastructure.dao.data.UserHolder;
import com.mijack.zero.common.base.BaseConverter;
import com.mijack.zero.framework.ddd.Repo;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

/**
 * @author Mi&amp;Jack
 */
@Repo
public class UserRepository extends BaseConverter<UserDO, User> {
    @Resource
    UserDao userDao;

    /**
     * 根据用户名查找对应的用户
     *
     * @param name 用户名
     * @return 如果未查询到，返回null
     */
    public User findOneByName(String name) {
        return convert(userDao.findOne(q -> q.eq(UserDO::getName, name)));
    }

    /**
     * 根据邮箱名查找对应的用户
     *
     * @param email 邮箱名
     * @return 如果未查询到，返回null
     */
    public User findOneByEmail(String email) {
        return convert(userDao.findOne(q -> q.eq(UserHolder::getEmail, email)));
    }

    public User getUserById(long userId) {
        return Optional.ofNullable(userDao.selectById(userId)).map(this::convert).orElse(null);
    }

    public long addUser(@NotNull User user) {
        return userDao.insert(reverse().convert(user));
    }

    public long updateUser(User user) {
        return userDao.updateById(reverse().convert(user));
    }

    public Long allocateId() {
        return userDao.allocateId();
    }

    public List<User> list() {
        return Lists.newArrayList(convertAll(userDao.selectList(null)));
    }

    public long delete(User user) {
        return userDao.deleteById(user.getId());
    }

    @Override
    protected UserDO doBackward(@Nonnull User input) {
        UserDO output = new UserDO();
        output.setId(input.getId());
        output.setEmail(input.getEmail());
        output.setCreateTime(input.getCreateTime());
        output.setUpdateTime(input.getUpdateTime());
        output.setName(input.getName());
        return output;
    }

    @Override
    protected User doForward(@Nonnull UserDO input) {
        User output = new User();
        output.setId(input.getId());
        output.setEmail(input.getEmail());
        output.setCreateTime(input.getCreateTime());
        output.setUpdateTime(input.getUpdateTime());
        output.setName(input.getName());
        return output;
    }
}
