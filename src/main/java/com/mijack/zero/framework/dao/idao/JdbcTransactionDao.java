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

package com.mijack.zero.framework.dao.idao;

import com.mijack.zero.framework.dao.exceptions.DaoException;
import com.mijack.zero.framework.dao.idata.Data;
import com.mijack.zero.framework.dao.idata.DataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mi&amp;Jack
 */
public interface JdbcTransactionDao<D extends Data<D> & DataHolder<D>> extends IDao<D> {
    Logger logger = LoggerFactory.getLogger(JdbcTransactionDao.class);

    /**
     * 开启一个事务
     *
     * @throws DaoException 事务处理异常
     */
    default void beginTransaction() throws DaoException {
        logger.info("beginTransaction: dao = {}", getClass());
    }

    /**
     * 提交一个事务
     *
     * @throws DaoException 事务处理异常
     */
    default void commitTransaction() throws DaoException {
        logger.info("commitTransaction: dao = {}", getClass());
    }

    /**
     * 回滚一个事务
     *
     * @throws DaoException 事务处理异常
     */
    default void rollbackTransaction() throws DaoException {
        logger.info("rollbackTransaction: dao = {}", getClass());
    }
}
