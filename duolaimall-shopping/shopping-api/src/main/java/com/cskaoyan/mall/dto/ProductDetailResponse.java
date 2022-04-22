package com.cskaoyan.mall.dto;


import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;



@Data
public class ProductDetailResponse extends AbstractResponse {
    private ProductDetailDto productDetailDto;
}
