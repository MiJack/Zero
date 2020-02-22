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

package com.mijack.zero.app.config;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import com.mijack.zero.app.enums.StorageType;
import com.mijack.zero.app.meta.resource.ZResource;
import com.mijack.zero.app.service.resource.ResourceService;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Component
public class AppConfigManager {
    public static final Logger logger = LoggerFactory.getLogger(AppConfigManager.class);
    @Resource
    private ResourceService resourceService;
    @Value("${application.config.avatar.classpath}")
    private String avatarLocalPath;
    @Value("${application.config.avatar.oss-location}")
    private String avatarOssLocation;
    @Getter
    private Long defaultAvatarResourceId;

    public void initManager() throws Exception {
        logger.info("initManager ");
        ClassPathResource classPathResource = new ClassPathResource(avatarLocalPath);
        File file = classPathResource.getFile();
        List<ZResource> resources = resourceService.getResourceAtStorageType(StorageType.ALIYUN, avatarOssLocation);
        if (CollectionUtils.isEmpty(resources)) {
            logger.info("加载默认头像: avatarLocalPath = {}, avatarOssLocation = {}", avatarLocalPath, avatarOssLocation);
            defaultAvatarResourceId = resourceService.allocateAliYunOssFromLocalFile(file, "默认头像", avatarOssLocation).getId();
        } else {
            // log
            defaultAvatarResourceId = resources.get(0).getId();
            logger.info("默认头像已存在: resourceId = {}", defaultAvatarResourceId);
        }
        logger.info("defaultAvatarResourceId = {}", defaultAvatarResourceId);
    }
}
