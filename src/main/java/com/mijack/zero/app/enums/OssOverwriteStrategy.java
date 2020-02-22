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

package com.mijack.zero.app.enums;

import com.mijack.zero.common.IdentifiableEnum;
import lombok.Getter;

/**
 * @author Mi&amp;Jack
 */
public enum OssOverwriteStrategy implements IdentifiableEnum<OssOverwriteStrategy> {
    /**
     * 拒绝申请
     */
    REJECT(0, "拒绝申请（资源占位符存在）"),
    /**
     * 占位符资源存在，但是资源未上传完成，原有上传失效，重新上传（导致原有上传文件丢失）
     */
    RE_UPLOAD_ON_UNCOMPLETED_UPLOAD(1, "占位符资源存在，但是资源未上传完成，原有上传失效，重新上传（导致原有上传文件丢失）"),
    /**
     * 占位符资源存在，系统决定一个可用的资源占位符
     */
    NEW_RESOURCE_KEY(2, "占位符资源存在，系统决定一个可用的资源占位符"),
    ;
    @Getter
    private final int id;
    @Getter
    private final String desc;

    OssOverwriteStrategy(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
