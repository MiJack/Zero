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

package com.mijack.zero.app.dao.token;

import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mijack.zero.app.meta.ApiToken;
import com.mijack.zero.app.meta.TokenStatus;
import com.mijack.zero.app.meta.TokenType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mi&amp;Jack
 */
@Mapper
public interface TokenDao extends BaseMapper<ApiToken> {
    default List<ApiToken> findLoginTokenByUserId(Long userId) {
        return selectList(
                new QueryWrapper<ApiToken>().lambda().eq(ApiToken::getResourceId, userId)
                        .eq(ApiToken::getType, TokenType.USER_LOGIN)
                        .eq(ApiToken::getTokenStatus, TokenStatus.VALID));
    }

    default int markApiTokenStatus(Long id, TokenStatus status) {
        return Optional.ofNullable(id)
                .map(this::selectById)
                .map(apiToken -> update(apiToken, new UpdateWrapper<ApiToken>().lambda().set(ApiToken::getTokenStatus, status))).orElse(0);
    }
}
