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

package com.mijack.zero.framework.mapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Mi&amp;Jack
 */
public interface BaseBeanMapper<DOMAIN, DTO extends Serializable> {
    DTO transformDomain(DOMAIN domain);

    default List<DTO> transformList(List<DOMAIN> domainList) {
        if (CollectionUtils.isEmpty(domainList)) {
            return Collections.emptyList();
        }
        return domainList.stream().map(this::transformDomain).collect(Collectors.toList());
    }
}
