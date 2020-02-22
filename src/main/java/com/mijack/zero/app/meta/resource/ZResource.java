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

package com.mijack.zero.app.meta.resource;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mijack.zero.app.enums.ZResourceStatus;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
@TableName("Zero_Resource")
public class ZResource implements Serializable {
    private static final long serialVersionUID = -7368045744884416175L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 储存类型
     */
    private Integer storageType;
    /**
     * 内容类型
     */
    private String contentType;
    /**
     * 内容名称
     */
    private String contentName;
    /**
     * 资源内容
     */
    private String storageLocation;
    /**
     * 资源文件大小
     */
    private Long contentLength;
    /**
     * 文件md5
     */
    private String md5;
    /**
     * 资源状态
     *
     * @see ZResourceStatus#getId()
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
