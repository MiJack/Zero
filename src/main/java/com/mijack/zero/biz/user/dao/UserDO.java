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

package com.mijack.zero.biz.user.dao;

import javax.annotation.Nullable;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

/**
 * @author Mi&Jack
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDO extends UserHolder implements IdentifiableData<Long, UserDO>, DeletableDo<UserDO> {
    /**
     * 用户id
     */
    private Long id;

    @Nullable
    public static UserDO from(User user) {
        if (user == null) {
            return null;
        }
        return UserMapper.INSTANCE.carToCarDto(user);
    }

    @Nullable
    public static User to(UserDO userDO) {
        if (userDO == null) {
            return null;
        }
        return UserMapper.INSTANCE.carToCarDto(userDO);
    }
}
