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

package com.mijack.zero.biz.user.infrastructure.dao;

import java.util.Optional;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.infrastructure.dao.data.UserDO;
import com.mijack.zero.common.dao.Table;
import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.BasicDao;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

/**
 * 用户存储管理
 *
 * @author Mi&Jack
 */
@Repository
@Table(name = "Zero_User")
public interface UserRepository extends BasicDao<Long, UserDO> {

    /**
     * 根据用户名查找对应的用户
     *
     * @param name 用户名
     * @return 如果未查询到，返回null
     */
    default UserDO findOneByName(String name) {
        return findOne(Criteria.eq("name", name));
    }

    /**
     * 根据邮箱名查找对应的用户
     *
     * @param email 邮箱名
     * @return 如果未查询到，返回null
     */
    default UserDO findOneByEmail(String email) {
        return findOne(Criteria.eq("email", email));
    }

    default User getUserById(long userId) {
        return Optional.ofNullable(getById(userId)).map(INSTANCE::formDo).orElse(null);
    }

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default long addUser(@NotNull User user) {
        return addDo(INSTANCE.toDo(user));
    }

    default long updateUser(User user) {
        return update(INSTANCE.toDo(user));
    }

    @Mapper
    interface UserMapper {

        @Mappings(value = {})
        UserDO toDo(User user);

        @Mappings(value = {})
        User formDo(UserDO userDO);
    }


}
