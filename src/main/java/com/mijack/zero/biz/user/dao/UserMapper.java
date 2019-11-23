package com.mijack.zero.biz.user.dao;

import com.mijack.zero.biz.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author yuanyujie
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings(value = {})
    UserDO carToCarDto(User car);

    @Mappings(value = {})
    User carToCarDto(UserDO car);
}
