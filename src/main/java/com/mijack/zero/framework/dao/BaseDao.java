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

package com.mijack.zero.framework.dao;

import static com.mijack.zero.framework.constant.ReflectConstant.TYPE_RESOLVER;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.classmate.ResolvedType;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;
import org.springframework.beans.BeanUtils;

/**
 * @author Mi&amp;Jack
 */
public interface BaseDao<T extends IdentifiableData<Long, T> & DataHolder<T>> extends BaseMapper<T> {
    /**
     * 申请一个id
     *
     * @return 申请的id
     */
    @Nullable
    default Long allocateId() {
        T t = BeanUtils.instantiateClass(getDataClazz());
        int insert = insert(t);
        if (insert > 0) {
            return t.getId();
        }
        return null;
    }

    /**
     * 给定条件查询一个do对象
     *
     * @param function 用于填充query条件
     * @return
     */
    default T findOne(Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> function) {
        return selectOne(function.apply(newQuery()));
    }

    /**
     * 获取query查询的构造体
     *
     * @return
     */
    default LambdaQueryWrapper<T> newQuery() {
        return new QueryWrapper<T>().lambda();
    }

    /**
     * 获取dao对应的do对象的class对象
     *
     * @return
     */
    default Class<T> getDataClazz() {
        ResolvedType resolve = TYPE_RESOLVER.resolve(getClass());
        if (resolve.isInstanceOf(BaseDao.class)) {
            List<ResolvedType> resolvedTypes = resolve.typeParametersFor(BaseDao.class);
            ResolvedType domainType = resolvedTypes.get(0);
            @SuppressWarnings("unchecked")
            Class<T> erasedType = (Class<T>) domainType.getErasedType();
            return erasedType;
        }
        throw new UnsupportedOperationException();
    }
}
