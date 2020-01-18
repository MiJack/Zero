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

package com.mijack.zero.framework.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Mi&amp;Jack
 */
@Import({WebMvcConfiguration.class, WebSecurityConfig.class})
@ImportResource("classpath:/config/server.yaml")
public class ServerConfig {
    public static final String DEFAULT_BUCKET="mijack-aliyun";
    /**
     * aliyun oss 配置
     * todo 后续支持多bucket环境
     *
     * @param endpoint
     * @param accessKeyId
     * @param secretAccessKey
     * @return
     */
    @Bean(name = "aliYunOssClient",destroyMethod = "shutdown")
    OSS ossClient(
            @Value("${storage.aliyun-oss.endpoint}") String endpoint,
            @Value("${storage.aliyun-oss.accessKeyId}") String accessKeyId,
            @Value("${storage.aliyun-oss.secretAccessKey}") String secretAccessKey) {
        return new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);
    }

}
