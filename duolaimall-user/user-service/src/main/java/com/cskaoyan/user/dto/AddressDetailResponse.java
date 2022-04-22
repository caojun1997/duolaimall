package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class AddressDetailResponse extends AbstractResponse {
	private AddressDto addressDto;
    
}
