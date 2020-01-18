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

import static com.mijack.zero.framework.config.ServerConfig.DEFAULT_BUCKET;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.annotation.Resource;

import com.aliyun.oss.OSS;
import com.mijack.zero.app.dao.ResourceDao;
import com.mijack.zero.app.enums.StorageType;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.ZResource;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

/**
 * @author Mi&amp;Jack
 */
@Service
public class ResourceService {
    public static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    @Resource
    private ResourceDao resourceDao;

    @Resource(name = "localUriBuilder")
    UriBuilder uriBuilder;

    @Resource(name = "aliYunOssClient")
    OSS aliYunOssClient;
    /**
     * 设置URL过期时间为1小时
     */
    public static final Long DEFAULT_OSS_EXPIRATION_TIME = TimeUnit.HOURS.toMillis(1);

    @Nullable
    public URI loadResourceUrl(Long resourceId) {
        ZResource zResource = Optional.ofNullable(resourceId).map(resourceDao::selectById)
                .orElse(null);
        if (zResource == null) {
            return null;
        }
        StorageType storageType = EnumUtils.idOf(zResource.getStorageType(), StorageType.class);
        Assert.notNull(storageType, () -> BaseBizException.createException("不支持当前储存方式"));
        try {
            switch (storageType) {
                case LOCAL:
                    return uriBuilder.path(zResource.getContent()).build();
                case ALIYUN:
                    return aliYunOssClient.generatePresignedUrl(DEFAULT_BUCKET, zResource.getContent(), new Date(System.currentTimeMillis() + DEFAULT_OSS_EXPIRATION_TIME))
                            .toURI();
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("loadResourceUrl error: resourceId = {}", resourceId, e);
            return null;
        }
    }

}
