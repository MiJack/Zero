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

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mijack.zero.app.dao.token.TokenDao;
import com.mijack.zero.app.meta.ApiToken;
import com.mijack.zero.app.meta.constant.TokenStatus;
import com.mijack.zero.app.meta.constant.TokenType;
import com.mijack.zero.app.common.Assert;
import com.mijack.zero.app.exception.BaseBizException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserTokenService {
    @Resource
    TokenDao tokenDao;

    public ApiToken generationUserApiToken(Long userId) {
        List<ApiToken> existApiTokens = tokenDao.findLoginTokenByUserId(userId);
        if (CollectionUtils.isNotEmpty(existApiTokens)) {
            for (ApiToken apiToken : existApiTokens) {
                tokenDao.markApiTokenStatus(apiToken.getId(), TokenStatus.INVALID);
            }
        }
        ApiToken apiToken = new ApiToken();
        apiToken.setResourceId(userId);
        apiToken.setType(TokenType.USER_LOGIN);
        long expireTime = System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY;
        apiToken.setExpire(new Timestamp(expireTime));
        // todo 检查token的唯一性
        apiToken.setTokenStatus(TokenStatus.VALID);
        apiToken.setToken(Base64.encodeBase64String((userId + "" + expireTime).getBytes()));
        tokenDao.insert(apiToken);
        Assert.state(apiToken.getId() > 0, () -> BaseBizException.createException("创建token失败"));
        return apiToken;
    }

    public ApiToken findToken(TokenType tokenType, String token) {
        return tokenDao.selectList(new QueryWrapper<ApiToken>().lambda()
                .eq(ApiToken::getTokenStatus, TokenStatus.VALID)
                .eq(ApiToken::getType, tokenType)
                .eq(ApiToken::getToken, token)).stream().findFirst().orElse(null);
    }
}
