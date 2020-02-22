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

package com.mijack.zero.framework.oss;

import static com.mijack.zero.framework.config.ServerConfig.DEFAULT_BUCKET;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.Resource;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Component
public class OssManager implements DisposableBean {
    public static final Logger logger = LoggerFactory.getLogger(OssManager.class);
    @Resource
    private OSS oss;

    @Nullable
    public URI generatePresignedUrl(String content, Long expirationTime) {
        try {
            URL url = oss.generatePresignedUrl(DEFAULT_BUCKET, content, new Date(System.currentTimeMillis() + expirationTime));
            if (url != null) {
                return url.toURI();
            }
            return null;
        } catch (Exception e) {
            logger.error("generatePresignedUrl error: content = {}, expirationTime = {}", content, expirationTime, e);
            return null;
        }
    }

    @Override
    public void destroy() throws Exception {
        oss.shutdown();
    }

    public void uploadAliYunOss(String ossKey, byte[] content) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(DEFAULT_BUCKET, ossKey, new ByteArrayInputStream(content));
        oss.putObject(putObjectRequest);
    }
}
