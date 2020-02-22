/*
 *     Copyright 2020 Mi&Jack
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

package com.mijack.zero.app.dao;

import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mijack.zero.app.enums.StorageType;
import com.mijack.zero.app.enums.ZResourceStatus;
import com.mijack.zero.app.meta.resource.ZResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Mapper
@Component
public interface ResourceDao extends BaseMapper<ZResource> {
    default List<ZResource> getResourceAtStorageType(StorageType storageType, String storageValue) {
        return selectList(new QueryWrapper<ZResource>().lambda()
                .eq(ZResource::getStorageType, storageType.getId())
                .eq(ZResource::getStorageLocation, storageValue)
        );
    }

    default int updateResoueceStatus(Long resourceId, ZResourceStatus status) {
        return Optional.ofNullable(selectById(resourceId))
                .map(zResource -> update(zResource, new UpdateWrapper<ZResource>().lambda().set(ZResource::getStatus, status.getId())))
                .orElse(0);
    }
}
