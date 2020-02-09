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

package com.mijack.zero.app.facade.account;

import java.net.URI;
import java.util.Optional;

import javax.annotation.Resource;

import com.mijack.zero.app.meta.AccountType;
import com.mijack.zero.app.meta.UserAccount;
import com.mijack.zero.app.service.account.AccountTypeService;
import com.mijack.zero.app.service.account.UserAccountService;
import com.mijack.zero.app.service.resource.ResourceService;
import com.mijack.zero.app.vo.AccountTypeVo;
import com.mijack.zero.app.vo.UserAccountDetailVo;
import org.springframework.stereotype.Service;

/**
 * @author Mi&amp;Jack
 */
@Service
public class UserAccountFacadeService {
    @Resource
    UserAccountService userAccountService;
    @Resource
    AccountTypeService accountTypeService;
    @Resource
    private ResourceService resourceService;

    public UserAccountDetailVo getUserAccountDetail(Long userId, Long accountId) {
        UserAccount userAccount = userAccountService.getUserAccountInfo(userId, accountId);
        if (userAccount == null) {
            return null;
        }
        Long accountTypeId = userAccount.getAccountTypeId();
        AccountType accountType = accountTypeService.findAccountTypeById(accountTypeId);
        if (accountType == null) {
            return null;
        }
        return buildUserAccountDetailVo(userAccount,accountType);
    }

    private UserAccountDetailVo buildUserAccountDetailVo(UserAccount userAccount, AccountType accountType) {
        AccountTypeVo accountTypeVo = transformAccountTypeVo(accountType);

        UserAccountDetailVo accountVo = new UserAccountDetailVo();
        accountVo.setId(userAccount.getId());
        accountVo.setUserId(userAccount.getUserId());
        accountVo.setNumber(userAccount.getNumber());
        accountVo.setTitle(userAccount.getTitle());
        accountVo.setAccountType(accountTypeVo);

        return accountVo;
    }

    public AccountTypeVo transformAccountTypeVo(AccountType accountType) {
        AccountTypeVo accountTypeVo = new AccountTypeVo();
        accountTypeVo.setBillingType(accountType.getBillingType().getDesc());
        accountTypeVo.setId(accountType.getId());
        accountTypeVo.setName(accountType.getName());
        accountTypeVo.setIconUrl(Optional.ofNullable(resourceService.loadResourceUrl(accountType.getIconId()))
                .map(URI::toString).orElse(null));
        return accountTypeVo;
    }
}
