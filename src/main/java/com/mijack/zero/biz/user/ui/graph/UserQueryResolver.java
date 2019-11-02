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

package com.mijack.zero.biz.user.ui.graph;

import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mi&Jack
 */

@Component
public class UserQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private UserService userService;

    public List<User> getUsers(final int count) {
        return this.userService.listUser(0, count);
    }

    public Optional<User> getUser(final long id) {
        return Optional.ofNullable(this.userService.getUser(id));
    }
}