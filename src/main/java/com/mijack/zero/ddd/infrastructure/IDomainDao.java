/*
 * Copyright 2019 Mi&Jack
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mijack.zero.ddd.infrastructure;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.domain.DeletableDomain;
import com.mijack.zero.ddd.infrastructure.criteria.Criteria;
import com.mijack.zero.utils.CollectionHelper;

/**
 * @param <Key>    领域对象的主键类型
 * @param <Domain> 领域对象
 * @author Mi&Jack
 */
public interface IDomainDao<Key, Domain extends BaseDomain<Key>> {
    /**
     * 查找一个领域模型
     *
     * @param key 待查询的领域对象的主键
     * @return 如果未查询到，返回null
     */
    default Domain findOne(Key key) {
        @NotNull List<Domain> list = findList(Collections.singletonList(key));
        return CollectionHelper.firstValue(list, null);
    }

    /**
     * 给定待查询的领域对象的主键列表，查找领域模型
     *
     * @param keys 待查询的领域对象的主键列表
     * @return 如果未查询到，返回空集合{@link Collections#emptyList()}
     */
    @NotNull
    List<Domain> findList(List<Key> keys);

    /**
     * 查找一个领域模型
     *
     * @param criteria 查询条件
     * @return 如果未查询到，返回null
     */
    default Domain findOne(Criteria criteria) {
        @NotNull List<Domain> list = findList(criteria);
        return CollectionHelper.firstValue(list, null);
    }

    /**
     * 给定待查询的领域对象的主键列表，查找领域模型
     *
     * @param criteria
     * @return 如果未查询到，返回空集合{@link Collections#emptyList()}
     */
    @NotNull
    List<Domain> findList(Criteria criteria);

    /**
     * 添加一个领域对象
     *
     * @param domain
     * @return 如果添加成功，返回1，反之返回0
     */
    default long add(Domain domain) {
        return add(Collections.singletonList((domain)));
    }

    /**
     * 添加若干个领域对象
     *
     * @param domains
     * @return 如果添加成功，返回添加成功的个数，反之返回0
     */
    long add(List<Domain> domains);

    /**
     * 更新一个领域对象
     *
     * @param domain
     * @return 如果添加成功，返回1，反之返回0
     */
    default long update(Domain domain) {
        return update(Collections.singletonList(domain));
    }

    /**
     * 更新若干个领域对象
     *
     * @param domains
     * @return 如果添加成功，返回添加成功的个数，反之返回0
     */
    long update(List<Domain> domains);

    /**
     * 删除领域对象，如果是{@link DeletableDomain}，标记删除状态为true；反之物理删除
     *
     * @param key 待删除的领域对象的主键
     * @return 如果删除成功，返回1，反之返回0
     */
    default long delete(Key key) {
        return delete(Collections.singletonList(key));
    }

    /**
     * 删除领域对象，如果是{@link DeletableDomain}，标记删除状态为true；反之物理删除
     *
     * @param keys 待删除的领域对象的主键列表
     * @return 如果删除成功，返回删除成功的个数，反之返回0
     */
    long delete(List<Key> keys);

    /**
     * 申请新的主键，用于新对象的创建
     *
     * @return
     */
    Key allocateKey();
}
