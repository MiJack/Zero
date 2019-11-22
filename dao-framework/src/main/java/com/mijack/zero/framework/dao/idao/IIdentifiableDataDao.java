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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idata.Data;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;

/**
 * 针对存储对象<code>D</code>的存储能力定义
 *
 * @param <ID> 主键类型
 * @param <D>  DB存储对象对应的java类型
 * @author Mi&Jack
 */
public interface IIdentifiableDataDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>> extends IDao<D> {

    /**
     * 删除能力的定义
     *
     * @param <ID> 主键类型
     * @param <D>  DB存储对象对应的java类型
     */
    interface IDeleteDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>> extends IIdentifiableDataDao<ID, D>, IDao.IDeleteDao<D> {
        /**
         * 给定条件删除数据，如果D为类型DeletableDo，将删除位置为true，反正进行物理删除
         *
         * @param id id标识
         * @return 删除成功的数目
         * @see DeletableDo <D>
         */
        default int delete(ID id) {
            return delete(Collections.singletonList(id));
        }

        /**
         * 给定条件删除数据，如果D为类型DeletableDo，将删除位置为true，反正进行物理删除
         *
         * @param ids id标识
         * @return 删除成功的数目
         */
        default int delete(List<ID> ids) {
            return delete(Criteria.in("id", ids));
        }

    }

    /**
     * 插入能力的定义
     *
     * @param <D> DB存储对象对应的java类型
     */
    interface IInsertDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>>
            extends IIdentifiableDataDao<ID, D>, IDao.IInsertDao<D> {
        /**
         * 申请新的主键，用于新对象的创建
         *
         * @return 申请的新主键
         */
        default ID allocateId() {
            List<ID> ids = allocateIds(1);
            if (ids.size() == 1) {
                return ids.get(0);
            }
            return null;
        }

        /**
         * 申请新的主键，用于新对象的创建
         *
         * @param number 申请的主键个数
         * @return 申请的新主键
         */
        @NotNull
        List<ID> allocateIds(int number);

        @Override
        default int addData(List<? extends DataHolder<D>> list) {
            List<ID> ids = insertData(list);
            return ids.size();
        }

        /**
         * 添加给定的数据，DH中如果包含主键，不会生效
         *
         * @param list 待添加的数据
         * @return 添加成功的数目
         */
        @NotNull
        List<ID> insertData(List<? extends DataHolder<D>> list);
    }

    /**
     * 查询能力的定义
     *
     * @param <ID> 主键类型
     * @param <D>  DB存储对象对应的java类型
     */
    interface IQueryDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>> extends IIdentifiableDataDao<ID, D>, IDao.IQueryDao<D> {
        /**
         * 查询给定主键的DB对象
         *
         * @param id 待查询的id
         * @return 查询得到的结果
         */
        default D getById(ID id) {
            List<D> dos = getByIds(Collections.singleton(id));
            return dos.isEmpty() ? null : dos.get(0);
        }

        /**
         * 查询给定主键的DB对象
         *
         * @param ids 待查询的id
         * @return 查询得到的结果
         */
        default List<D> getByIds(Collection<ID> ids) {
            return query(Criteria.in("id", ids));
        }

    }

    /**
     * 更新能力的定义
     *
     * @param <ID> 主键类型
     * @param <D>  DB存储对象对应的java类型
     */
    interface IUpdateDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>>
            extends IIdentifiableDataDao<ID, D>, IDao.IUpdateDao<D> {
        /**
         * 更新相关数据
         *
         * @param dh  待更新的数据
         * @param ids 待更新的数据
         * @return 更新的数目
         */
        default int update(DataHolder<D> dh, Collection<ID> ids) {
            return update(dh, Criteria.in("id", ids));
        }

        /**
         * 更新相关数据
         *
         * @param dh 待更新的数据
         * @param id 待更新的数据
         * @return 更新的数目
         */
        default int update(DataHolder<D> dh, ID id) {
            return update(dh, Collections.singleton(id));
        }
    }
}
