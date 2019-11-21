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

/**
 * @author Mi&Jack
 */
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


import java.lang.reflect.Proxy;

import com.mijack.zero.framework.dao.idao.IDao;


/**
 * @author Mi&Jack
 */
public class DaoFactory {
    public <ID,D extends DaoFactory<D>, DAO extends IDao<D>>
    DAO proxyForDao(Class<DAO> daoClazz) {
        @SuppressWarnings("unchecked")
        DAO domainDao = (DAO) Proxy.newProxyInstance(daoClazz.getClassLoader(), new Class[]{daoClazz},
                new DaoProxy<>(daoClazz));
        return domainDao;
    }
}
