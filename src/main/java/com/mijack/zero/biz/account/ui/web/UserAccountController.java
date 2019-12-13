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

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.ui.command.AccountCreateCommand;
import com.mijack.zero.biz.account.ui.command.AccountDeleteCommand;
import com.mijack.zero.biz.account.ui.command.AccountListCommand;
import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.domain.service.UserAccountService;
import com.mijack.zero.biz.account.ui.dto.UserAccountDto;
import com.mijack.zero.biz.account.ui.mapper.UserAccountMapper;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @Resource
    UserAccountMapper userAccountMapper;

    @GetMapping(value = "/account/list")
    public List<UserAccountDto> listUserAccount(@RequestParam AccountListCommand accountListCommand) {
        return userAccountMapper.transformList(userAccountService.listUserAccount(accountListCommand.getUserId()));
    }

    @PutMapping("/account/create")
    public UserAccountDto createAccount(@RequestParam AccountCreateCommand accountCreateCommand) {
        return userAccountMapper.transformDomain(userAccountService.createAccount(accountCreateCommand.getUserId(), accountCreateCommand.getAccountName(), accountCreateCommand.getAccountType()));
    }

    @DeleteMapping("/account/delete")
    public UserAccountDto deleteAccount(@RequestParam AccountDeleteCommand accountDeleteCommand) {
        return  userAccountMapper.transformDomain(userAccountService.deleteAccount(accountDeleteCommand.getUserId(), accountDeleteCommand.getAccountId()));
    }
}
