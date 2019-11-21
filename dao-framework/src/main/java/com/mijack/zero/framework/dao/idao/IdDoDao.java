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

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.meta.DataHolder;
import com.mijack.zero.framework.dao.meta.DeletableDo;
import com.mijack.zero.framework.dao.meta.IdDO;

/**
 * 针对存储对象<code>IdDo</code>的存储能力定义
 *
 * @param <ID>   主键类型
 * @param <IdDo> DB存储对象对应的java类型
 * @author Mi&Jack
 */
public interface IdDoDao<ID, IdDo extends IdDO<ID, IdDo>> extends IDao<IdDo> {

    /**
     * 删除能力的定义
     *
     * @param <ID>   主键类型
     * @param <IdDo> DB存储对象对应的java类型
     */
    interface IdDeleteDao<ID, IdDo extends IdDO<ID, IdDo>> extends IDeleteDao<IdDo> {
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
     * 查询能力的定义
     *
     * @param <ID>   主键类型
     * @param <IdDo> DB存储对象对应的java类型
     */
    interface IdQueryDao<ID, IdDo extends IdDO<ID, IdDo>> extends IQueryDao<IdDo> {
        /**
         * 查询给定主键的DB对象
         *
         * @param id 待查询的id
         * @return 查询得到的结果
         */
        default IdDo getById(ID id) {
            List<IdDo> dos = getByIds(Collections.singleton(id));
            return dos.isEmpty() ? null : dos.get(0);
        }

        /**
         * 查询给定主键的DB对象
         *
         * @param ids 待查询的id
         * @return 查询得到的结果
         */
        default List<IdDo> getByIds(Collection<ID> ids) {
            return query(Criteria.in("id", ids));
        }

    }

    /**
     * 更新能力的定义
     *
     * @param <ID>   主键类型
     * @param <IdDo> DB存储对象对应的java类型
     * @param <DH>   DB存储对象对应的java类型
     */
    interface IdUpdateDao<ID, IdDo extends IdDO<ID, IdDo> & DataHolder, DH extends DataHolder<IdDo>> extends IUpdateDao<IdDo, DH> {
        /**
         * 更新相关数据
         *
         * @param dh 待更新的数据
         * @param ids 待更新的数据
         * @return 更新的数目
         */
        default int update(DH dh, Collection<ID> ids) {
            return update(dh, Criteria.in("id", ids));
        }

        /**
         * 更新相关数据
         *
         * @param dh 待更新的数据
         * @param id 待更新的数据
         * @return 更新的数目
         */
        default int update(DH dh, ID id) {
            return update(dh, Collections.singleton(id));
        }
    }
}
