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

package com.mijack.zero.framework.dao.idata;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB存储对象
 *
 * @param <D>  DB存储对象对应的java类型
 * @author Mi&amp;Jack
 */
public interface Data<D extends Data<D> & DataHolder<D>> {
    Logger logger = Logger.getLogger("Data");

    /**
     * 将值从打他Holder拷贝到当前目录
     *
     * @param dataHolder 拷贝的值
     */
    default <DH extends DataHolder<D>> void loadData(DH dataHolder) {
        try {
            copyProperties(this, dataHolder);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.ALL, "set DataHolder error", e);
        }
    }
}
