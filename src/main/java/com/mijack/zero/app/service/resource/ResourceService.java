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
import java.util.List;
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
import org.apache.commons.collections.CollectionUtils;
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
    /**
     * 设置URL过期时间为1小时
     */
    public static final Long DEFAULT_OSS_EXPIRATION_TIME = TimeUnit.HOURS.toMillis(1);
    @Resource
    private ResourceDao resourceDao;

    @Resource(name = "localUriBuilderFactory")
    private DefaultUriBuilderFactory uriBuilderFactory;

    @Resource
    private OssManager ossManager;

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
            String storageValue = zResource.getStorageLocation();
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

    public ZResource createResource(StorageType storageType, String storageLocation, String contentType,
                                    String contentName, Long size, String md5) {
        ZResource zResource = new ZResource();

        zResource.setStorageType(storageType.getId());
        zResource.setStorageLocation(storageLocation);

        zResource.setContentType(contentType);
        zResource.setContentName(contentName);
        zResource.setContentLength(size);
        zResource.setMd5(md5);
        zResource.setStatus(ZResourceStatus.INIT.getId());
        Assert.state(resourceDao.insert(zResource) > 0, () -> BaseBizException.createException("创建资源失败"));
        return zResource;
    }

    public ZResource getResource(Long id) {
        return Optional.ofNullable(id).map(resourceDao::selectById).orElse(null);
    }

    public List<ZResource> getResourceAtStorageType(StorageType storageType, String storageValue) {
        return resourceDao.getResourceAtStorageType(storageType, storageValue);
    }

    public String decideOssKey(String ossKey, OssOverwriteStrategy overwriteStrategy) {
        List<ZResource> resource = getResourceAtStorageType(StorageType.ALIYUN, ossKey);
        OssOverwriteStrategy ossOverwriteStrategy = Optional.ofNullable(overwriteStrategy).orElse(OssOverwriteStrategy.REJECT);
        if (CollectionUtils.isEmpty(resource)) {
            return ossKey;
        }

        if (ossOverwriteStrategy.equals(OssOverwriteStrategy.REJECT)) {
            logger.error("oss对象已存在：ossKey  = {} ",ossKey);
            throw new BaseBizException(400, OssOverwriteStrategy.REJECT.getDesc());
        }
        throw new BaseBizException(400, "该上传策略暂不支持");
    }

    public boolean uploadAliYunOss(Long resourceId, byte[] content) {
        ZResource resource = getResource(resourceId);
        Assert.enumEquals(resource.getStorageType(), StorageType.ALIYUN, ("不支持当前存储类型"));
        ossManager.uploadAliYunOss(resource, content);
        return resourceDao.updateResoueceStatus(resourceId, ZResourceStatus.OK) > 0;
    }

    public ZResource allocateAliYunOss(String ossKey, String contentType, Long contentLength, String contentMD5, String contentName,
                                       OssOverwriteStrategy overwriteStrategy) {
        String storageLocation = decideOssKey(ossKey, overwriteStrategy);
        return createResource(StorageType.ALIYUN, storageLocation,
                contentType,
                Optional.ofNullable(contentName).orElse("未命名文件"), contentLength, contentMD5);

    }
}
