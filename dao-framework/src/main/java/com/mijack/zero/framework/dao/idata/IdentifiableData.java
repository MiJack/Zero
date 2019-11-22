/*
 *    Copyright 2019 Mi&Jack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mijack.zero.framework.dao.idata;

/**
 * 支持主键的DB存储对象
 *
 * @param <ID> DB存储对象对应的java类型的主键类型
 * @param <D>  DB存储对象对应的java类型
 * @author Mi&Jack
 */
public interface IdentifiableData<ID, D extends Data<D> & DataHolder<D>> extends Data<D> {
    /**
     * 获取主键
     *
     * @return 主键
     */
    ID getId();

    /**
     * 设置主键
     *
     * @param id 主键
     */
    void setId(ID id);
}
