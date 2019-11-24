package com.mijack.zero.common.enums;

/**
 * @author yuanyujie
 */
public interface IdentifiableEnum<E extends Enum<E> & IdentifiableEnum<E>> {
    /**
     * 枚举变量的id
     *
     * @return
     */
    int getId();
}
