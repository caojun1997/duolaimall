package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;


@Data
public class AllProductCateResponse extends AbstractResponse {
    private List<ProductCateDto> productCateDtoList;
}
