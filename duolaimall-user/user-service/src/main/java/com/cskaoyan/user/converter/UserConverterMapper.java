package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dto.UserLoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserConverterMapper {

    @Mappings({})
    UserLoginResponse converter(Member member);

}
