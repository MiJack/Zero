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

package com.mijack.zero.app.controller;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.app.common.web.mvc.ApiController;
import com.mijack.zero.app.meta.UserAccount;
import com.mijack.zero.app.service.UserAccountService;
import com.mijack.zero.app.command.AccountCreateCommand;
import com.mijack.zero.app.command.AccountDisableCommand;
import com.mijack.zero.app.command.AccountListCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api")
public class UserAccountController {
    @Resource
    UserAccountService userAccountService;

    @GetMapping(value = "/account/list")
    public List<UserAccount> listUserAccount(@RequestParam AccountListCommand accountListCommand) {
        return userAccountService.listUserAccount(accountListCommand.getUserId());
    }

    @PutMapping("/account/create")
    public UserAccount createAccount(@RequestParam AccountCreateCommand accountCreateCommand) {
        return userAccountService.createAccount(accountCreateCommand.getUserId(), accountCreateCommand.getAccountName(), accountCreateCommand.getAccountType());
    }

    @PutMapping("/account/disable")
    public UserAccount disableAccount(@RequestParam AccountDisableCommand accountDisableCommand) {
        return userAccountService.disableAccount(accountDisableCommand.getUserId(), accountDisableCommand.getAccountId());
    }
}

