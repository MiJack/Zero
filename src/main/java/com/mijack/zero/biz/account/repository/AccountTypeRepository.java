package com.mijack.zero.biz.account.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mijack.zero.biz.account.domain.AccountType;
import com.mijack.zero.biz.account.infrastructure.dao.data.AccountTypeDO;
import com.mijack.zero.common.repository.BaseMapper;
import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.BasicDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

/**
 * @author Mi&#38;Jack
 */
@Repository
public interface AccountTypeRepository extends BasicDao<Long, AccountTypeDO> {

    /**
     * 添加AccountType
     *
     * @param accountType
     * @return
     */
    default long addAccountType(AccountType accountType) {
        AccountTypeDO accountTypeDO = INSTANCE.toDo(accountType);
        return addDo(accountTypeDO);
    }

    AccountTypeMapper INSTANCE = Mappers.getMapper(AccountTypeMapper.class);

    /**
     * 给定id，返回对应的AccountType
     *
     * @param id
     * @return
     */
    default AccountType getAccountTypeById(Long id) {
        return Optional.ofNullable(getById(id)).map(INSTANCE::formDo).orElse(null);
    }

    /**
     * 列举所有的AccountType
     *
     * @return
     */
    default List<AccountType> listAccountType() {
        List<AccountTypeDO> result = query(Criteria.TRUE);
        return result.stream().map(INSTANCE::formDo).collect(Collectors.toList());
    }

    @Mapper
    interface AccountTypeMapper extends BaseMapper<AccountType, AccountTypeDO> {
        /**
         * {@inheritDoc}
         *
         * @param domain
         * @return
         */
        @Override
        @Mappings(value = {})
        AccountTypeDO toDo(AccountType domain);

        /**
         * {@inheritDoc}
         *
         * @param d
         * @return
         */
        @Override
        @Mappings(value = {})
        AccountType formDo(AccountTypeDO d);
    }

}
