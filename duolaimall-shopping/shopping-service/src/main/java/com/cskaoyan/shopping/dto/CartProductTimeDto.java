package com.cskaoyan.shopping.dto;/*
 *@Auther:强
 *@Date: 2022/4/23 17:11
 *@Version 1.0.0.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductTimeDto implements Serializable {
    private static final long serialVersionUID = -809047960626248847L;

    private Long productId;

    private BigDecimal salePrice;

    private Long productNum;

    private Long limitNum;

    private String checked;

    private String productName;

    private String productImg;

    Date cartAddTime;

}
