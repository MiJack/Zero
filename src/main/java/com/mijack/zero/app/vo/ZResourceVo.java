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

package com.mijack.zero.app.vo;

import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class ZResourceVo implements Serializable {
    private static final long serialVersionUID = -1101024737782224673L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 内容类型
     */
    private String contentType;
    /**
     * 资源内容
     */
    private URI uri;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
