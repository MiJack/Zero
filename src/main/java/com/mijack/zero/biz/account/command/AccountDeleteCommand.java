package com.mijack.zero.biz.account.command;

import lombok.Data;

/**
 * @author yuanyujie
 */
@Data
public class AccountDeleteCommand {
    private long userId;
    private long accountId;
}
