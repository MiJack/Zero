package com.mijack.zero.common.repository;

import com.mijack.zero.framework.dao.idata.Data;

/**
 * @author yuanyujie
 */
public interface BaseMapper<DOMAIN, DO extends Data<?>> {

    /**
     * 将domain转化成do对象
     *
     * @param domain
     * @return
     */
    DO toDo(DOMAIN domain);

    /**
     * 将do对象转化成domain
     *
     * @param d
     * @return
     */
    DOMAIN formDo(DO d);
}
