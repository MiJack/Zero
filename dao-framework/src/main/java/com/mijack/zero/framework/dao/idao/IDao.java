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

package com.mijack.zero.framework.dao.idao;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.Data;
import com.mijack.zero.framework.dao.idata.IdentifiableData;

/**
 * 针对存储对象<code>D</code>的存储能力定义
 *
 * @param <D> DB存储对象对应的java类型
 * @author Mi&Jack
 */
public interface IDao<D extends Data<D> & DataHolder<D>> {
    /**
     * DB存储对象对应的java类型
     *
     * @return DB存储对象对应的class对象
     */
    Class<D> getDataClazz();

    /**
     * 删除能力的定义
     *
     * @param <D> DB存储对象对应的java类型
     */
    interface IDeleteDao<D extends Data<D> & DataHolder<D>> extends IDao<D> {
        /**
         * 给定条件删除数据，如果D为类型DeletableDo，将删除位置为true，反正进行物理删除
         *
         * @param criteria 待删除数据的条件
         * @return 删除成功的数目
         * @see DeletableDo <D>
         */
        long delete(Criteria criteria);
    }

    /**
     * 插入能力的定义
     *
     * @param <D> DB存储对象对应的java类型
     */
    interface IInsertDao<D extends Data<D> & DataHolder<D>> extends IDao<D> {

        /**
         * 添加给定的数据。
         * 如果对象类型为IdDO，且未设置ID，返回时对象中的主键会被设置
         *
         * @param d 待添加的数据
         * @return 添加成功的数目
         * @see IdentifiableData
         */
        default long addDo(D d) {
            return addDo(Collections.singletonList(d));
        }

        /**
         * 添加给定的数据。
         * 如果对象类型为IdDO，未设置ID，执行insert操作，返回时对象中的主键会被设置；设置ID，执行update操作，不修改对象中的主键
         *
         * @param list 待添加的数据
         * @return 添加成功的数目
         * @note 如果存在list存在一个对象
         * @see IdentifiableData
         */
        default long addDo(List<D> list) {
            return addData(list);
        }

        /**
         * 添加给定的数据，DH中如果包含主键，不会生效
         *
         * @param list 待添加的数据
         * @return 添加成功的数目
         */
        long addData(List<? extends DataHolder<D>> list);
    }

    /**
     * 查询能力的定义
     *
     * @param <D> DB存储对象对应的java类型
     */
    interface IQueryDao<D extends Data<D> & DataHolder<D>> {
        /**
         * 给定条件进行查询
         *
         * @param criteria 待查询数据的条件
         * @return 查询得到的结果，结果为空，返回{@code Collections#emptyList() }
         * @see Collections#emptyList()
         */
        default D findOne(Criteria criteria) {
            List<D> list = query(criteria);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }

        /**
         * 给定条件进行查询
         *
         * @param criteria 待查询数据的条件
         * @return 查询得到的结果，结果为空，返回{@code Collections#emptyList() }
         * @see Collections#emptyList()
         */
       @NotNull List<D> query(Criteria criteria);
    }

    /**
     * 更新能力的定义
     *
     * @param <D> DB存储对象对应的java类型
     */
    interface IUpdateDao<D extends Data<D> & DataHolder<D>> extends IDao<D> {
        /**
         * 更新相关数据
         *
         * @param dh       待更新的数据
         * @param criteria 待删除的数据
         * @return 更新的数目
         */
        <DH extends DataHolder<D>> long update(DH dh, Criteria criteria);
    }

    interface IStatisticsDao<D extends Data<D> & DataHolder<D>> extends IDao<D> {
        /**
         * 统计所有的数据
         *
         * @return 数据总条数
         */
        default long count() {
            return count(Criteria.TRUE);
        }

        /**
         * 给定条件进行查询满足的条数
         *
         * @param criteria 待查询数据的条件
         * @return 数据总条数
         */
        long count(Criteria criteria);
    }

}
