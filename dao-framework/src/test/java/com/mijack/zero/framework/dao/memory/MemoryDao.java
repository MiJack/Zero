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

package com.mijack.zero.framework.dao.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.exceptions.DaoException;
import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.IdentifiableData;

/**
 * @author Mi&Jack
 */
public class MemoryDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>> implements BasicDao<ID, D> {
    private final static Logger logger = Logger.getLogger("MemoryDao");
    private final Class<D> dataClazz;
    private final Map<ID, D> domainMap = new HashMap<>(16);
    private final IDomainKeyGenerator<ID, D> domainKeyGenerator;
    private final CriteriaFilter criteriaFilter = new CriteriaFilter();

    public MemoryDao(Class<D> dataClazz, IDomainKeyGenerator<ID, D> domainKeyGenerator) {
        this.dataClazz = dataClazz;
        this.domainKeyGenerator = domainKeyGenerator;
    }

    @Override
    public Class<D> getDataClazz() {
        return dataClazz;
    }

    @Override
    public long delete(Criteria criteria) {
        List<D> query = query(criteria);
        return query.stream().map(D::getId).map(id -> domainMap.remove(id) != null).mapToInt(b -> b ? 1 : 0).reduce(Integer::sum).orElse(0);
    }

    @Override
    public List<D> query(Criteria criteria) {
        List<D> list = new ArrayList<>();
        for (D domain : domainMap.values()) {
            if (isValid(domain)) {
                if (criteriaFilter.doCriteria(domain, criteria)) {
                    list.add(domain);
                }
            }
        }
        return list;
    }

    protected boolean isValid(D domain) {
        return domain != null;
    }

    @Override
    public @NotNull
    List<ID> allocateIds(int number) {
        return domainKeyGenerator.allocateKeys(domainMap, number);
    }

    @Override
    public List<ID> insertData(List<? extends DataHolder<D>> list) {
        int count = list.size();
        @NotNull List<ID> ids = allocateIds(count);
        for (int i = 0; i < count; i++) {
            ID id = ids.get(i);
            DataHolder<D> dataHolder = list.get(i);
            D d = newDoInstance();
            d.loadData(dataHolder);
            domainMap.put(id, d);
        }
        return ids;
    }

    private D newDoInstance() {
        try {
            return getDataClazz().newInstance();
        } catch (Exception e) {
            logger.log(Level.ALL, "生成DO对象异常", e);
            throw new DaoException(e);
        }
    }

    @Override
    public long count(Criteria criteria) {
        return query(criteria).size();
    }

    @Override
    public <DH extends DataHolder<D>> long update(DH dh, Criteria criteria) {
        List<D> result = query(criteria);
        for (D d : result) {
            d.loadData(dh);
        }
        return result.size();
    }
}
