package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import com.cskaoyan.mall.dto.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class AllProductResponse2 {

    private List<ProductDto> data;

    private Long total;
}
