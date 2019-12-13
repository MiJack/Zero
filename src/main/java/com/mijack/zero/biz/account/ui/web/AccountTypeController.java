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

package com.mijack.zero.biz.account.ui.web;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.ui.command.AccountTypeCreateCommand;
import com.mijack.zero.biz.account.domain.service.AccountTypeService;
import com.mijack.zero.biz.account.ui.dto.AccountTypeDto;
import com.mijack.zero.biz.account.ui.mapper.AccountTypeMapper;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api")
public class AccountTypeController {
    @Resource
    AccountTypeMapper accountTypeMapper;
    @Resource
    AccountTypeService accountTypeService;

    @PutMapping("/account/type/create")
    public AccountTypeDto createAccountType(@RequestParam AccountTypeCreateCommand command) {
        return accountTypeMapper.transformDomain(accountTypeService.createAccountType(command.getTypeName(), command.getAccountTypeIcon(), command.getBillingType()));
    }

    @GetMapping("/account/type/list")
    public List<AccountTypeDto> listAccountType() {
        return accountTypeMapper.transformList(accountTypeService.listAccountType());
    }
}
