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

package com.mijack.zero.ddd.domain;

/**
 * 存在删除状态的bean
 *
 * @author Mi&Jack
 */
public interface DeletableDomain<Key> extends BaseDomain<Key> {

    /**
     * 是否被删除
     *
     * @return
     */
    boolean isDeleted();

    /**
     * 设置bean的删除状态
     *
     * @param deleted
     */
    void setDeleted(boolean deleted);
}
