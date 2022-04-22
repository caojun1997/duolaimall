package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Address;
import com.cskaoyan.user.dto.AddAddressRequest;
import com.cskaoyan.user.dto.AddressDto;
import com.cskaoyan.user.dto.UpdateAddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressConverter {

    @Mappings({})
    AddressDto address2List(Address addresses);

    /*@Mappings({})
    AddressDto address2Res(Address address);*/

    List<AddressDto> address2List(List<Address> addresses);

    @Mappings({})
    Address req2Address(AddAddressRequest request);

    @Mappings({})
    Address req2Address(UpdateAddressRequest request);
}
