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

package com.mijack.zero.biz.user.domain.cases;

import java.util.List;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.framework.cases.UseCase;

/**
 * @author Mi&amp;Jack
 */
public interface UserCase extends UseCase {

    interface UserManagerCase extends UserCase {
        User updateUserInfo(Long id, String name, String email);

        boolean deleteUser(Long userId);
    }

    interface UserRegisterCase extends UserCase {

        /**
         * 用于注册用户
         *
         * @param name
         * @param email
         * @param password
         * @return
         */
        User registerUser(String name, String email, String password);
    }

    interface UserLoginCase extends UserCase {

        /**
         * 用于登录用户
         *
         * @param type
         * @param email
         * @param password
         * @return
         */
        User loginUser(int type, String email, String password);
    }

    /**
     * 用户查询相关用例
     */
    interface UserQueryCase extends UserCase {
        /**
         * 列举用户
         *
         * @return
         */
        List<User> listUser();

        /**
         * 查询用户
         *
         * @param userId
         * @return
         */
        User getUser(Long userId);
    }
}
