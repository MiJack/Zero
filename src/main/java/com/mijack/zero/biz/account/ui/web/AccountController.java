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

package com.mijack.zero.biz.account.ui.web;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.command.AccountCreateCommand;
import com.mijack.zero.biz.account.domain.Account;
import com.mijack.zero.biz.account.service.AccountService;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author Mi&Jack
 */
@ApiController
public class AccountController {
    @Resource
    AccountService accountService;

    @PutMapping("/account/create")
    public Account createAccount(AccountCreateCommand accountCreateCommand) {
        return accountService.createAccount(accountCreateCommand.getUserId(), accountCreateCommand.getAccountName(), accountCreateCommand.getAccountType());
    }
}
