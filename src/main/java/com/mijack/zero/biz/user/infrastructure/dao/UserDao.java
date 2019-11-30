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

package com.mijack.zero.biz.user.infrastructure.dao;

import com.mijack.zero.biz.user.infrastructure.dao.data.UserDO;
import com.mijack.zero.common.dao.Table;
import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.ddd.Dao;

/**
 * 用户存储管理
 *
 * @author Mi&amp;Jack
 */
@Dao
@Table(name = "Zero_User")
public interface UserDao extends BasicDao<Long, UserDO> {


}
