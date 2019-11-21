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

package com.mijack.zero.dao;

import java.util.Objects;

import com.mijack.zero.framework.dao.idata.DataHolder;

/**
 * @author Mi&Jack
 */
public class RepData implements DataHolder<Repo> {
    private String user;
    private String name;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepData repData = (RepData) o;
        return Objects.equals(user, repData.user) &&
                Objects.equals(name, repData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
