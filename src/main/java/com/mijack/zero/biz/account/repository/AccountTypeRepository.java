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
 * @author Mi&amp;Jack
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
