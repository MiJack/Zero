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

package com.mijack.zero.framework.dao.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;

/**
 * @author Mi&Jack
 */
public class MemoryBasicDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>> implements BasicDao<ID, D> {
    private Class<D> dataClazz;
    private Map<ID, D> map = new HashMap<>(16);

    public MemoryBasicDao(Class<D> dataClazz) {
        this.dataClazz = dataClazz;
    }

    @Override
    public Class<D> getDataClazz() {
        return dataClazz;
    }

    @Override
    public int delete(Criteria criteria) {
        return 0;
    }

    @Override
    public int addData(List<? extends DataHolder<D>> list) {
        return 0;
    }

    @Override
    public List<D> query(Criteria criteria) {
        return null;
    }

    @Override
    public int update(DataHolder<D> dh, Criteria criteria) {
        return 0;
    }
}
