package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class AddCartResponse extends AbstractResponse {
    private CartProductTimeDto AddCartResultItemDtos;
}
