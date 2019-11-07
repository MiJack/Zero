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

package com.mijack.zero.ddd.infrastructure;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.mijack.zero.ddd.domain.BaseDomain;
import com.mijack.zero.ddd.domain.DeletableDomain;

import com.google.common.collect.Maps;

/**
 * @param <Key>    领域对象的主键类型
 * @param <Domain> 领域对象
 * @author Mi&Jack
 */
public abstract class MemoryDomainDao<Key, Domain extends BaseDomain<Key>> implements IDomainDao<Key, Domain> {
    private Map<Key, Domain> domainMap = Maps.newHashMap();

    @Override
    public @NotNull List<Domain> findList(List<Key> keys) {
        return keys.stream().filter(Objects::nonNull).map(key -> domainMap.get(key)).filter(this::isValid).collect(Collectors.toList());
    }

    protected boolean isValid(Domain domain) {
        if (domain == null) {
            return false;
        }
        if (domain instanceof DeletableDomain) {
            return !((DeletableDomain) domain).isDeleted();
        }
        return true;
    }

    @Override
    public long add(List<Domain> domains) {
        for (Domain domain : domains) {
            if (domainMap.containsKey(domain.getId())) {
                continue;
            }
            domainMap.put(domain.getId(), domain);
        }
        return domains.size();
    }

    @Override
    public long update(List<Domain> domains) {
        return domains.stream().filter(domain -> domainMap.containsKey(domain.getId()))
                .filter(this::isValid)
                .map(domain -> domainMap.put(domain.getId(), domain)).count();
    }

    @Override
    public long delete(List<Key> keys) {
        return keys.stream().filter(key -> domainMap.containsKey(key))
                .map(key -> {
                    Domain domain = domainMap.get(key);
                    if (domain instanceof DeletableDomain) {
                        ((DeletableDomain) domain).setDeleted(true);
                    } else {
                        domainMap.remove(key);
                    }
                    return domain;
                }).count();
    }

}
