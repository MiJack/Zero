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
import org.springframework.beans.BeanUtils;

/**
 * @param <K> 领域对象的主键类型
 * @param <D> 领域对象
 * @author Mi&Jack
 */
public interface IDomainDao<K, D extends BaseDomain<K>> {
    /**
     * 查找一个领域模型
     *
     * @param key 待查询的领域对象的主键
     * @return 如果未查询到，返回null
     */
    default D findOne(K key) {
        @NotNull List<D> list = list(Collections.singletonList(key));
        return CollectionHelper.firstValue(list, null);
    }

    /**
     * 给定待查询的领域对象的主键列表，查找领域模型
     *
     * @param keys 待查询的领域对象的主键列表
     * @return 如果未查询到，返回空集合{@link Collections#emptyList()}
     */
    @NotNull
    List<D> list(List<K> keys);

    /**
     * 统计表里未删除的记录数
     *
     * @return 未删除的记录数
     */
    default Long count() {
        if (isDeletableDomain()) {
            return countBy(Criteria.eq("deleted", 0));
        }
        return countBy(Criteria.TRUE);
    }

    /**
     * 判断当前对象是否为DeletableDomain
     *
     * @return 对象是否删除
     * @see DeletableDomain
     */
    default boolean isDeletableDomain() {
        @SuppressWarnings("unchecked")
        Class<D> domainClazz = DomainDaoUtils.getDomainClass((Class<? super IDomainDao<?, D>>) getClass());
        return domainClazz.isAssignableFrom(DeletableDomain.class);
    }

    /**
     * 给定待查询的领域对象的查询条件，返回对应的个数
     *
     * @param criteria 查询条件
     * @return 满足查询条件的记录数
     */
    Long countBy(Criteria criteria);

    /**
     * 查找一个领域模型
     *
     * @param criteria 查询条件
     * @return 如果未查询到，返回null
     */
    default D queryOne(Criteria criteria) {
        @NotNull List<D> list = queryList(criteria);
        return CollectionHelper.firstValue(list, null);
    }

    /**
     * 给定待查询的领域对象的查询条件，查找领域模型
     *
     * @param criteria 查询条件
     * @return 如果未查询到，返回空集合{@link Collections#emptyList()}
     */
    @NotNull
    List<D> queryList(Criteria criteria);

    /**
     * 添加一个领域对象
     *
     * @param domain 领域对象
     * @return 如果添加成功，返回1，反之返回0
     */
    default long add(D domain) {
        return add(Collections.singletonList((domain)));
    }

    /**
     * 添加若干个领域对象
     *
     * @param domains 领域对象
     * @return 如果添加成功，返回添加成功的个数，反之返回0
     */
    long add(List<D> domains);

    /**
     * 更新一个领域对象
     *
     * @param domain 领域对象
     * @return 如果添加成功，返回1，反之返回0
     */
    default long update(D domain) {
        return update(Collections.singletonList(domain));
    }

    /**
     * 更新若干个领域对象
     *
     * @param domains 领域对象
     * @return 如果添加成功，返回添加成功的个数，反之返回0
     */
    long update(List<D> domains);

    /**
     * 删除领域对象，如果是{@link DeletableDomain}，标记删除状态为true；反之物理删除
     *
     * @param key 待删除的领域对象的主键
     * @return 如果删除成功，返回1，反之返回0
     */
    default long delete(K key) {
        return delete(Collections.singletonList(key));
    }

    /**
     * 删除领域对象，如果是{@link DeletableDomain}，标记删除状态为true；反之物理删除
     *
     * @param keys 待删除的领域对象的主键列表
     * @return 如果删除成功，返回删除成功的个数，反之返回0
     */
    long delete(List<K> keys);

    /**
     * 申请新的主键，用于新对象的创建
     *
     * @return 申请的新主键
     */
    default K allocateKey() {
        @SuppressWarnings("unchecked")
        Class<D> domainClazz = DomainDaoUtils.<D>getDomainClass((Class<? super IDomainDao<?, D>>) getClass());
        D domain = BeanUtils.instantiateClass(domainClazz);
        if (add(domain) == 1) {
            return domain.getId();
        }
        return null;
    }

    /**
     * 申请一个对象
     *
     * @return 申请的对象
     */
    default D allocate() {
        @SuppressWarnings("unchecked")
        Class<D> domainClazz = (Class<D>) DomainDaoUtils.getDomainClass((Class<? super IDomainDao<K, D>>) getClass());
        D domain = BeanUtils.instantiateClass(domainClazz);
        if (add(domain) == 1) {
            return domain;
        }
        return null;
    }

    /**
     * 列举出所有对象
     *
     * @return 所有的对象
     */
    default List<D> listAll() {
        return queryList(Criteria.TRUE);
    }
}
