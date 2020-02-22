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

package com.mijack.zero.app.service.resource;


import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;

import com.mijack.zero.app.dao.ResourceDao;
import com.mijack.zero.app.enums.AccountTypeEnums;
import com.mijack.zero.app.enums.OssOverwriteStrategy;
import com.mijack.zero.app.enums.StorageType;
import com.mijack.zero.app.enums.ZResourceStatus;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.resource.ZResource;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.EnumUtils;
import com.mijack.zero.framework.oss.OssManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author Mi&amp;Jack
 */
@Service
public class ResourceService {
    public static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    private static final String OSS_KEY_PREFIX = "Zero/Resources/";
    @Resource
    private ResourceDao resourceDao;

    @Resource(name = "localUriBuilderFactory")
    DefaultUriBuilderFactory uriBuilderFactory;

    @Resource
    OssManager ossManager;
    /**
     * 设置URL过期时间为1小时
     */
    public static final Long DEFAULT_OSS_EXPIRATION_TIME = TimeUnit.HOURS.toMillis(1);

    @Nullable
    public URI loadResourceUrl(Long resourceId) {
        if (resourceId == null) {
            return null;
        }
        if (resourceId < 0) {
            AccountTypeEnums accountTypeEnums = EnumUtils.keyOfEnum(-resourceId, AccountTypeEnums::getId, AccountTypeEnums.class);
            if (accountTypeEnums != null) {
                return pathToUrl(accountTypeEnums.getImagePath());
            }
        }
        ZResource zResource = Optional.of(resourceId).map(resourceDao::selectById)
                .orElse(null);
        if (zResource == null) {
            return null;
        }
        StorageType storageType = EnumUtils.idOfEnum(zResource.getStorageType(), StorageType.class);
        Assert.notNull(storageType, () -> BaseBizException.createException("不支持当前储存方式"));
        try {
            String storageValue = zResource.getStorageValue();
            switch (storageType) {
                case LOCAL:
                    // todo 避免硬编码
                    return pathToUrl(storageValue);
                case ALIYUN:
                    return ossManager.generatePresignedUrl(storageValue, DEFAULT_OSS_EXPIRATION_TIME);
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("loadResourceUrl error: resourceId = {}", resourceId, e);
            return null;
        }
    }

    @Nonnull
    protected URI pathToUrl(String content) {
        if (content.startsWith("static/")) {
            content = content.substring("static".length());
        }
        return uriBuilderFactory.builder().path(content).build();
    }

    public ZResource createResource(StorageType storageType, String contentType, String content, String md5,
                                    Long size, ZResourceStatus resourceStatus) {
        ZResource zResource = new ZResource();
        zResource.setStorageValue(content);
        zResource.setStorageType(storageType.getId());
        zResource.setContentType(contentType);
        zResource.setSize(size);
        zResource.setMd5(md5);
        zResource.setStatus(resourceStatus.getId());
        Assert.state(resourceDao.insert(zResource) > 0, () -> BaseBizException.createException("创建资源失败"));
        return zResource;
    }

    public ZResource getResource(Long id) {
        return Optional.ofNullable(id).map(resourceDao::selectById).orElse(null);
    }

    public ZResource getResourceAtStorageType(StorageType storageType, String storageValue) {
        return resourceDao.getResourceAtStorageType(storageType, storageValue);
    }

    public String decideOssKey(String ossKey, Integer overwriteStrategy) {
        ZResource resource = getResourceAtStorageType(StorageType.ALIYUN, ossKey);
        OssOverwriteStrategy ossOverwriteStrategy = EnumUtils.idOfEnum(overwriteStrategy, OssOverwriteStrategy.class, OssOverwriteStrategy.REJECT);
        if (resource == null) {
            return OSS_KEY_PREFIX + ossKey;
        }

        if (ossOverwriteStrategy.equals(OssOverwriteStrategy.REJECT)) {
            throw new BaseBizException(400, OssOverwriteStrategy.REJECT.getDesc());
        }
        throw new BaseBizException(400, "该上传策略暂不支持");
    }

    public boolean uploadAliYunOss(Long resourceId, byte[] content) {
        ZResource resource = getResource(resourceId);
        Assert.enumEquals(resource.getStorageType(), StorageType.ALIYUN, ("不支持当前存储类型"));
        ossManager.uploadAliYunOss(resource.getStorageValue(), content);
        return resourceDao.updateResoueceStatus(resourceId, ZResourceStatus.OK) > 0;
    }
}
