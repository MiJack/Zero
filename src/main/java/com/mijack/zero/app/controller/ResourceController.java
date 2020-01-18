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

package com.mijack.zero.app.controller;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Nullable;
import javax.annotation.Resource;

import com.mijack.zero.app.command.ResourceCreateCommand;
import com.mijack.zero.app.enums.StorageType;
import com.mijack.zero.app.meta.ZResource;
import com.mijack.zero.app.service.resource.ResourceService;
import com.mijack.zero.app.vo.ZResourceVo;
import com.mijack.zero.framework.web.mvc.ApiController;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/resource")
public class ResourceController {
    @Resource
    ResourceService resourceService;

    @RequestMapping("/create")
    public ZResource createResource(ResourceCreateCommand createCommand) throws IOException {
        if (StorageType.LOCAL.isThisValue(createCommand.getStorageType())) {
            ClassPathResource classPathResource = new ClassPathResource(createCommand.getContent());
            String md5Hex = DigestUtils.md5Hex(classPathResource.getInputStream());
            createCommand.setMd5(md5Hex);
        }
        return resourceService.createResource(createCommand.getStorageType(),
                createCommand.getContentType(),
                createCommand.getContent(), createCommand.getMd5());
    }

    @Nullable
    @RequestMapping("/info/get")
    public ZResourceVo getResourceInfo(@RequestParam(name = "id") Long id) {
        ZResource resource = resourceService.getResource(id);
        if (resource == null) {
            return null;
        }
        ZResourceVo resourceVo = new ZResourceVo();
        URI uri = resourceService.loadResourceUrl(id);
        resourceVo.setId(resource.getId());
        resourceVo.setContentType(resource.getContentType());
        resourceVo.setCreateTime(resource.getCreateTime());
        resourceVo.setUpdateTime(resource.getUpdateTime());
        resourceVo.setMd5(resource.getMd5());
        resourceVo.setUri(uri);
        return resourceVo;
    }
}
