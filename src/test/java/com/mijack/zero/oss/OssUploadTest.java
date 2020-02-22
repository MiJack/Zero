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

package com.mijack.zero.oss;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import com.mijack.zero.app.enums.OssOverwriteStrategy;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.app.meta.resource.ZResource;
import com.mijack.zero.app.service.resource.ResourceService;
import com.mijack.zero.framework.config.ServerConfig;
import org.apache.tika.Tika;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

/**
 * @author Mi&amp;Jack
 */
@SpringBootTest(classes = {ServerConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class OssUploadTest {
    public static final Logger logger = LoggerFactory.getLogger(OssUploadTest.class);
    @Resource
    ResourceService resourceService;
    Tika tika = new Tika();
    public static final String UPLOAD_FILE = "src/main/java/com/mijack/zero/app/controller/oss/AliYunOssController.java";

    @Test
    public void testOssUpload() throws IOException {
        File file = new File(UPLOAD_FILE);
        String ossKey = UUID.randomUUID().toString();
        uploadFile(file, ossKey);
    }

    @Test(expected = BaseBizException.class)
    public void testOssUploadRepeat() throws IOException {
        File file = new File(UPLOAD_FILE);
        String ossKey = UUID.randomUUID().toString();
        uploadFile(file, ossKey);
        uploadFile(file, ossKey);
    }

    protected void uploadFile(File file, String ossKey) throws IOException {
        String uploadOssKey = "_tmp/" + ossKey;
        HashCode md5Hash = Files.asByteSource(file).hash(Hashing.md5());
        String contentType = tika.detect(file);
        long contentLength = file.length();


        logger.info("try to upload file: location = {}, md5 = {}, uploadOssKey = {}", file.getAbsolutePath(), md5Hash.toString(), uploadOssKey);
        ZResource zResource = resourceService.allocateAliYunOss(uploadOssKey, contentType, contentLength, md5Hash.toString(), "",
                OssOverwriteStrategy.REJECT);
        Long zResourceId = zResource.getId();
        logger.info("allocateAliYunOss: resourceId = {}, storageLocation = {}", zResourceId, zResource.getStorageLocation());
        byte[] bytes = Files.asByteSource(file).read();

        boolean uploadResult = resourceService.uploadAliYunOss(zResourceId, bytes);
        Assert.assertTrue("上传不成功", uploadResult);

        ZResource downloadResource = resourceService.getResource(zResourceId);
        logger.info("downloadResource = {}", downloadResource);
    }
}
