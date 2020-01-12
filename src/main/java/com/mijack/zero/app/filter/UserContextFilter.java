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

package com.mijack.zero.app.filter;


import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mijack.zero.app.context.UserContext;
import com.mijack.zero.app.meta.ApiToken;
import com.mijack.zero.app.meta.TokenType;
import com.mijack.zero.app.service.user.UserTokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Component
public class UserContextFilter implements Filter {
    public static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);
    public static final String API_HEAD_NAME = "X-ZERO-API-TOKEN";
    @Resource
    UserTokenService userTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String apiToken = httpServletRequest.getHeader(API_HEAD_NAME);
        if (StringUtils.isNotEmpty(apiToken)) {
            ApiToken token = userTokenService.findToken(TokenType.USER_LOGIN, apiToken);
            if (token != null) {
                UserContext userContext = UserContext.getCurrent();
                userContext.setUserId(token.getResourceId());
            }
        }
        chain.doFilter(request, response);
        UserContext.clear();
    }
}
