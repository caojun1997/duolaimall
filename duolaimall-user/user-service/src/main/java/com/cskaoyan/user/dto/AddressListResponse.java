package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;


@Data
public class AddressListResponse extends AbstractResponse {

    private List<AddressDto> addressDtos;
}
