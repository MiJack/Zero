package com.mijack.zero.biz.account.repository;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mijack.zero.biz.account.domain.UserAccount;
import com.mijack.zero.biz.account.infrastructure.dao.AccountTypeDao;
import com.mijack.zero.biz.account.infrastructure.dao.UserAccountDao;
import com.mijack.zero.biz.account.infrastructure.dao.data.UserAccountDO;
import com.mijack.zero.biz.user.infrastructure.dao.UserRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yuanyujie
 */
@Repository
public class UserAccountRepository {
    final UserRepository userRepository;
    final UserAccountDao userAccountDao;
    final AccountTypeDao accountTypeDao;

    public UserAccountRepository(UserRepository userRepository, UserAccountDao userAccountDao, AccountTypeDao accountTypeDao) {
        this.userRepository = userRepository;
        this.userAccountDao = userAccountDao;
        this.accountTypeDao = accountTypeDao;
    }

    public List<UserAccount> listUserAccount(long userId) {
        @NotNull List<UserAccountDO> userAccountDOS = userAccountDao.listUserAccount(userId);
        List<UserAccount> userAccounts = new ArrayList<>();
        for (UserAccountDO userAccountDO : userAccountDOS) {
            UserAccount userAccount = new UserAccount();
            userAccount.setName(userAccountDO.getName());
            userAccount.setId(userAccountDO.getId());
            userAccount.setUser(userRepository.getUserById(userId));
            userAccount.setAccountType(accountTypeDao.getAccountTypeById(userAccountDO.getAccountType()));
            userAccounts.add(userAccount);
        }
        return userAccounts;
    }

    public void addUserAccount(UserAccount userAccount) {
        //TODO: to write the method addUserAccount
        throw new UnsupportedOperationException();
    }
}
