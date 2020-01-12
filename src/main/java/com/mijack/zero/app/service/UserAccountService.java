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

package com.mijack.zero.app.service;

import java.util.List;

import com.mijack.zero.app.meta.UserAccount;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserAccountService {
    public List<UserAccount> listUserAccount(long userId) {
        return null;
    }

    public UserAccount createAccount(long userId, String accountName, Long accountType) {
        return null;
    }

    public UserAccount disableAccount(long userId, long accountId) {
        return null;
    }
}
