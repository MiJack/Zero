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

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mijack.zero.app.command.AccountCreateCommand;
import com.mijack.zero.app.command.AccountDisableCommand;
import com.mijack.zero.app.command.AccountListCommand;
import com.mijack.zero.app.facade.account.UserAccountFacadeService;
import com.mijack.zero.app.meta.AccountType;
import com.mijack.zero.app.meta.UserAccount;
import com.mijack.zero.app.service.account.AccountTypeService;
import com.mijack.zero.app.service.account.UserAccountService;
import com.mijack.zero.app.service.resource.ResourceService;
import com.mijack.zero.app.vo.AccountTypeVo;
import com.mijack.zero.app.vo.UserAccountDetailVo;
import com.mijack.zero.app.vo.UserAccountVo;
import com.mijack.zero.framework.context.UserContext;
import com.mijack.zero.framework.web.mvc.ApiController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * todo 支持分页查询
 *
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/user/account")
public class UserAccountController {
    @Resource
    UserAccountService userAccountService;
    @Resource
    AccountTypeService accountTypeService;
    @Resource
    UserAccountFacadeService userAccountFacadeService;

    @GetMapping(value = "/list")
    public List<UserAccountVo> listUserAccount(AccountListCommand accountListCommand) {
        long userId = accountListCommand.getUserId();
        if (userId <= 0) {
            userId = UserContext.getCurrentUserId();
        }
        List<UserAccount> userAccounts = userAccountService.listUserAccount(userId);
        List<Long> accountTypeIds = userAccounts.stream().map(UserAccount::getAccountTypeId).distinct()
                .collect(Collectors.toList());
        List<AccountType> accountTypes = accountTypeService.findAccountTypeByIds(accountTypeIds);
        Map<Long, AccountTypeVo> accountTypeMap = Maps.newHashMap();
        for (AccountType accountType : accountTypes) {
            accountTypeMap.put(accountType.getId(), userAccountFacadeService.transformAccountTypeVo(accountType));
        }
        List<UserAccountVo> list = Lists.newArrayList();
        for (UserAccount userAccount : userAccounts) {
            UserAccountVo accountVo = new UserAccountVo();
            accountVo.setId(userAccount.getId());
            accountVo.setUserId(userAccount.getUserId());
            accountVo.setNumber(userAccount.getNumber());
            accountVo.setTitle(userAccount.getTitle());
            accountVo.setAccountType(accountTypeMap.get(userAccount.getAccountTypeId()));
            list.add(accountVo);
        }
        return list;
    }

    @PostMapping("/create")
    public UserAccount createUserAccount(AccountCreateCommand accountCreateCommand) {
        long userId = accountCreateCommand.getUserId();
        if (userId <= 0) {
            userId = UserContext.getCurrentUserId();
        }
        return userAccountService.createAccount(userId, accountCreateCommand.getAccountName(), accountCreateCommand.getAccountNumber(), accountCreateCommand.getAccountType());
    }

    @DeleteMapping("/delete")
    public UserAccount deleteUserAccount(@RequestParam AccountDisableCommand accountDisableCommand) {
        return userAccountService.deleteAccount(accountDisableCommand.getUserId(), accountDisableCommand.getAccountId());
    }

    @GetMapping("/info/{id}")
    public UserAccountDetailVo getUserAccountInfo(@PathVariable("id") Long accountId) {
        Long userId = UserContext.getCurrentUserId();
        return userAccountFacadeService.getUserAccountDetail(userId, accountId);
    }
}

