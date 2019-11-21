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

import java.util.logging.Logger;

import com.mijack.zero.framework.dao.factory.DaoFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mi&Jack
 */
public class DaoCase {
    private Logger logger = Logger.getLogger("daoCase");

    @Test
    public void doTest() {
        DaoFactory daoFactory = new DaoFactory();
        Long id = 1L;
        Repo repo = new Repo();
        repo.setId(id);
        repo.setName("repo");
        repo.setUser("user");
        RepoDao repoDao = daoFactory.proxyForDao(RepoDao.class);
        int i = repoDao.addDo(repo);
        Assert.assertEquals("插入成功为1", i, 1);
        Repo repoInDb = repoDao.getById(id);

    }
}
