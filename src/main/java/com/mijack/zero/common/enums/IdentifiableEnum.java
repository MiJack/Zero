package com.mijack.zero.common.enums;

/**
 * @author yuanyujie
 */
public interface IdentifiableEnum<E extends Enum<E> & IdentifiableEnum<E>> {
    int getId();
}
