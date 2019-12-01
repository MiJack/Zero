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

package com.mijack.zero.biz.user.infrastructure.dao.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mi&amp;Jack
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName( "Zero_User")
public class UserDO extends UserHolder implements IdentifiableData<Long, UserDO>, DeletableDo<UserDO> {
    /**
     * 用户id
     */
    private Long id;
}
