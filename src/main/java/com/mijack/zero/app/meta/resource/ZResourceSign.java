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

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 资源文件的内容签名
 *
 * @author Mi&amp;Jack
 */
@Data
@TableName("Zero_ResourceSign")
public class ZResourceSign implements Serializable {
    private static final long serialVersionUID = 1791513604444033963L;
    /**
     * 主键
     */
    private long id;
    /**
     * 资源id
     */
    private long resourceId;
    /**
     * 签名类型
     */
    private long signType;
    /**
     * 签名内容
     */
    private String signContent;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
