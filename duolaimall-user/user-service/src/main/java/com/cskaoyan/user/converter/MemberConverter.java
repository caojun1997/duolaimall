package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dto.QueryMemberResponse;
import com.cskaoyan.user.dto.UpdateMemberRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MemberConverter {

    @Mappings({})
    QueryMemberResponse member2Res(Member member);

    @Mappings({})
    Member updateReq2Member(UpdateMemberRequest request);
}
