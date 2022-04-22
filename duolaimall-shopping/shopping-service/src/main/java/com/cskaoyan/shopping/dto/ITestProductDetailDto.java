package com.cskaoyan.shopping.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ITestProductDetailDto {

    String productName;

    BigDecimal price;

    String imgUrls;

}
