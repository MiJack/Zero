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

package com.mijack.zero.app.controller.oss;

import java.net.URI;

import javax.annotation.Nullable;
import javax.annotation.Resource;

import com.mijack.zero.app.command.oss.OssAllocateCommand;
import com.mijack.zero.app.command.oss.OssUploadCommand;
import com.mijack.zero.app.enums.OssOverwriteStrategy;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.resource.ZResource;
import com.mijack.zero.app.service.resource.ResourceService;
import com.mijack.zero.app.vo.ZResourceVo;
import com.mijack.zero.common.EnumUtils;
import com.mijack.zero.framework.web.mvc.ApiController;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mi&amp;Jack
 */
@ApiController(path = "/api/resource/aliyun/oss")
public class AliYunOssController {
    public static final DataSize DEFAULT_SIMPLE_UPLOAD_SIZE_LIMIT = DataSize.parse("1MB");
    @Resource
    ResourceService resourceService;

    @PostMapping("/allocate")
    public ZResource allocateAliYunOss(@RequestBody OssAllocateCommand ossAllocateCommand) {
        if (ossAllocateCommand.getContentLength() >= DEFAULT_SIMPLE_UPLOAD_SIZE_LIMIT.toBytes()) {
            throw new BaseBizException(400, "上传文件过大");
        }
        return resourceService.allocateAliYunOss(ossAllocateCommand.getOssKey(),
                ossAllocateCommand.getContentType(),
                ossAllocateCommand.getContentLength(),
                ossAllocateCommand.getContentMD5(),
                ossAllocateCommand.getContentName(),
                EnumUtils.idOfEnum(ossAllocateCommand.getOverwriteStrategy(), OssOverwriteStrategy.class, OssOverwriteStrategy.REJECT));
    }


    @PostMapping("/upload")
    public ZResourceVo uploadAliYunOss(@RequestBody OssUploadCommand ossUploadCommand) {
        if (ossUploadCommand.getContent().length >= DEFAULT_SIMPLE_UPLOAD_SIZE_LIMIT.toBytes()) {
            throw new BaseBizException(400, "上传文件过大");
        }
        boolean uploadAliYunOss = resourceService.uploadAliYunOss(ossUploadCommand.getResourceId(), ossUploadCommand.getContent());
        if (!uploadAliYunOss) {
            throw new BaseBizException(500, "上传文件失败");
        }
        return getResourceInfo(ossUploadCommand.getResourceId());
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
        resourceVo.setUri(uri);
        return resourceVo;
    }

}
