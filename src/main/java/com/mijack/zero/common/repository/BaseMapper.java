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

package com.mijack.zero.common.repository;

import com.mijack.zero.framework.dao.idata.Data;

/**
 * @author Mi&amp;Jack
 */
public interface BaseMapper<DOMAIN, DO extends Data<?>> {

    /**
     * 将domain转化成do对象
     *
     * @param domain
     * @return
     */
    DO toDo(DOMAIN domain);

    /**
     * 将do对象转化成domain
     *
     * @param d
     * @return
     */
    DOMAIN formDo(DO d);
}
