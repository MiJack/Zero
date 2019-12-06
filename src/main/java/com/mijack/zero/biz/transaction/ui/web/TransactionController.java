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

package com.mijack.zero.biz.transaction.ui.web;

import javax.annotation.Resource;

import com.mijack.zero.biz.transaction.command.TransactionAttachCommand;
import com.mijack.zero.biz.transaction.command.TransactionRemoveCommand;
import com.mijack.zero.biz.transaction.domain.Transaction;
import com.mijack.zero.biz.transaction.service.TransactionService;
import com.mijack.zero.common.web.mvc.ApiController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/transaction")
public class TransactionController {
    @Resource
    TransactionService transactionService;

    @RequestMapping("/attach")
    public Transaction attachTransaction(TransactionAttachCommand transactionAttachCommand) {
        return transactionService.attachTransaction(transactionAttachCommand);
    }

    @RequestMapping("/remove")
    public boolean attachTransaction(TransactionRemoveCommand transactionAttachCommand) {
        return transactionService.removeTransaction(transactionAttachCommand);
    }
}