/*
 *    Copyright 2019 Mi&Jack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mijack.zero.framework.dao.idao;

import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;

/**
 * 基础dao能力的集合
 *
 * @author Mi&amp;Jack
 */
@Deprecated
public interface BasicDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>>
        extends IIdentifiableDataDao.IInsertDao<ID, D>, IIdentifiableDataDao.IQueryDao<ID, D>, IIdentifiableDataDao.IDeleteDao<ID, D>,
        IIdentifiableDataDao.IUpdateDao<ID, D>, IDao.IStatisticsDao<D>, JdbcTransactionDao<D> {
}
