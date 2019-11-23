package com.mijack.zero.biz.account.infrastructure.dao;

import java.util.Optional;

import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.infrastructure.dao.data.AccountTypeDO;
import com.mijack.zero.framework.dao.idao.BasicDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

/**
 * @author yuanyujie
 */
@Repository
public interface AccountTypeDao extends BasicDao<Long, AccountTypeDO> {

    default long addAccountType(AccountType accountType) {
        AccountTypeDO accountTypeDO = INSTANCE.toDo(accountType);
        return addDo(accountTypeDO);
    }

    AccountTypeMapper INSTANCE = Mappers.getMapper(AccountTypeMapper.class);

    default AccountType getAccountTypeById(Long id) {
        return Optional.ofNullable(getById(id)).map(INSTANCE::formDo).orElse(null);
    }

    @Mapper
    interface AccountTypeMapper {

        @Mappings(value = {})
        AccountTypeDO toDo(AccountType car);

        @Mappings(value = {})
        AccountType formDo(AccountTypeDO car);
    }

}
