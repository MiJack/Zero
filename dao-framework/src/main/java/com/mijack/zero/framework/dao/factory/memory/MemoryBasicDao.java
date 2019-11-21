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

package com.mijack.zero.framework.dao.factory.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mijack.zero.framework.dao.Criteria;
import com.mijack.zero.framework.dao.idao.BasicDao;
import com.mijack.zero.framework.dao.idata.DataHolder;
import com.mijack.zero.framework.dao.idata.DeletableDo;
import com.mijack.zero.framework.dao.idata.IdentifiableData;

/**
 * @author Mi&Jack
 */
public class MemoryBasicDao<ID, D extends IdentifiableData<ID, D> & DataHolder<D>> implements BasicDao<ID, D> {
    private Class<D> daoInterface;
    private Map<ID, D> domainMap = new HashMap<>(16);
    private final IDomainKeyGenerator<ID, D> domainKeyGenerator;
    private final CriteriaFilter criteriaFilter;

    public MemoryBasicDao(Class<D> daoInterface, IDomainKeyGenerator<ID, D> domainKeyGenerator, CriteriaFilter criteriaFilter) {
        this.daoInterface = daoInterface;
        this.domainKeyGenerator = domainKeyGenerator;
        this.criteriaFilter = criteriaFilter;
    }

    @Override
    public Class<D> getDataClazz() {
        return daoInterface;
    }

    @Override
    public int delete(Criteria criteria) {
        List<D> query = query(criteria);
        return query.stream().map(D::getId).map(id -> domainMap.remove(id) != null).mapToInt(b -> b ? 1 : 0).reduce(Integer::sum).orElse(0);
    }

    @Override
    public int addData(List<? extends DataHolder<D>> list) {
        throw new UnsupportedOperationException();
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

    @Override
    public int update(DataHolder<D> dh, Criteria criteria) {
        throw new UnsupportedOperationException();
    }

    protected boolean isValid(D domain) {
        if (domain == null) {
            return false;
        }
        if (domain instanceof DeletableDo) {
            @SuppressWarnings("unchecked")
            DeletableDo<D> deletableDomain = (DeletableDo<D>) domain;
            return !(deletableDomain).isDeleted();
        }
        return true;
    }


    public ID allocateKey() {
        return domainKeyGenerator.allocateKey(domainMap);
    }

}
