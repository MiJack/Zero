package com.mijack.zero.biz.account.ui.web;

import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.biz.account.command.AccountTypeCreateCommand;
import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.service.AccountTypeService;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yuanyujie
 */
@ApiController(path = "/api")
public class AccountTypeController {
    @Resource
    private AccountTypeService accountTypeService;

    @PutMapping("/account/type/create")
    public AccountType createAccountType(@RequestParam AccountTypeCreateCommand command) {
        return accountTypeService.createAccountType(command.getTypeName(), command.getAccountTypeIcon(), command.getBillingType());
    }

    @GetMapping("/account/type/list")
    public List<AccountType> listAccountType() {
        return accountTypeService.listAccountType();
    }
}
