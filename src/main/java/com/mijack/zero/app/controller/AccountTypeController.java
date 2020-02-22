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
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mijack.zero.app.facade.account.UserAccountFacadeService;
import com.mijack.zero.app.vo.AccountTypeVo;
import com.mijack.zero.framework.web.mvc.ApiController;
import com.mijack.zero.app.meta.account.AccountType;
import com.mijack.zero.app.service.account.AccountTypeService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/account/type")
public class AccountTypeController {
    @Resource
    AccountTypeService accountTypeService;
    @Resource
    UserAccountFacadeService userAccountFacadeService;

    @GetMapping("/list")
    public List<AccountTypeVo> listAccountType() {
        List<AccountType> accountTypes = accountTypeService.listAccountType();
        return accountTypes.stream()
                .map(accountType -> userAccountFacadeService.transformAccountTypeVo(accountType))
                .collect(Collectors.toList());
    }
}
