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

package com.mijack.zero.app.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mijack.zero.app.meta.account.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Mapper
@Component
public interface UserAccountDao extends BaseMapper<UserAccount> {
    default List<UserAccount> selectByUserAccount(long userId){
        return selectList(new QueryWrapper<UserAccount>().lambda().eq(UserAccount::getUserId,userId));
    }
}
