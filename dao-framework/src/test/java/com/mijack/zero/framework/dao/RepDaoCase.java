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

package com.mijack.zero.framework.dao;

import java.util.logging.Logger;

import com.mijack.zero.demo.Repo;
import com.mijack.zero.framework.dao.iface.RepoDao;
import com.mijack.zero.framework.dao.memory.MemoryDaoProxy;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mi&amp;Jack
 */
public class RepDaoCase {
    private Logger logger = Logger.getLogger("daoCase");

    @Test
    public void doTest() {
        Long id = 1L;
        Repo repo = new Repo();
        repo.setId(id);
        repo.setName("repo");
        repo.setUser("user");
        RepoDao repoDao = MemoryDaoProxy.defaultProxyForDao(RepoDao.class);
        long count = repoDao.count();
        long i = repoDao.addDo(repo);
        Assert.assertEquals("插入成功为1", i, 1);
        long newCount = repoDao.count();
        Assert.assertEquals("数据库中的数据应该发生变化", count + i, newCount);
        Repo repoInDb = repoDao.getById(id);
        Assert.assertEquals("db存储和内存应该保持一致", repo, repoInDb);
    }
}
