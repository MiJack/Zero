package com.mijack.zero.biz.account.repository;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.infrastructure.dao.UserAccountDao;
import com.mijack.zero.biz.account.infrastructure.dao.data.UserAccountDO;
import com.mijack.zero.biz.user.infrastructure.dao.UserRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Converter;

/**
 * @author yuanyujie
 */
@Repository
public class UserAccountRepository extends Converter<UserAccountDO, UserAccount> {
    final UserRepository userRepository;
    final UserAccountDao userAccountDao;
    final AccountTypeRepository accountTypeRepository;

    public UserAccountRepository(UserRepository userRepository, UserAccountDao userAccountDao, AccountTypeRepository accountTypeRepository) {
        this.userRepository = userRepository;
        this.userAccountDao = userAccountDao;
        this.accountTypeRepository = accountTypeRepository;
    }

    public List<UserAccount> listUserAccount(long userId) {
        @NotNull List<UserAccountDO> accountsInDb = userAccountDao.listUserAccount(userId);
        List<UserAccount> userAccounts = new ArrayList<>();
        for (UserAccountDO userAccountDO : accountsInDb) {
            userAccounts.add(convert(userAccountDO));
        }
        return userAccounts;
    }

    public long addUserAccount(UserAccount userAccount) {
        return userAccountDao.addDo(reverse().convert(userAccount));
    }

    @Override
    protected UserAccountDO doBackward(@org.jetbrains.annotations.NotNull UserAccount userAccount) {
        UserAccountDO userAccountDO = new UserAccountDO();
        userAccountDO.setId(userAccount.getId());
        userAccountDO.setAccountType(userAccount.getAccountType().getId());
        userAccountDO.setName(userAccount.getName());
        userAccountDO.setUserId(userAccount.getUser().getId());
        return userAccountDO;
    }

    @Override
    protected UserAccount doForward(@org.jetbrains.annotations.NotNull UserAccountDO userAccountDO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(userAccountDO.getName());
        userAccount.setId(userAccountDO.getId());
        userAccount.setUser(userRepository.getUserById(userAccountDO.getUserId()));
        userAccount.setAccountType(accountTypeRepository.getAccountTypeById(userAccountDO.getAccountType()));
        return userAccount;
    }

    public UserAccount findUserAccount(long userId, long accountId) {
        UserAccountDO userAccountDO = userAccountDao.findByUserIdAndAccountId(userId, accountId);
        return convert(userAccountDO);
    }

    public boolean deleteAccount(UserAccount userAccount) {
        return userAccountDao.deleteByUserIdAndAccountId(userAccount.getId(), userAccount.getUser().getId()) > 0;
    }
}
