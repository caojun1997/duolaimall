package com.cskaoyan.shopping.dto;/*
 *@Auther:强
 *@Date: 2022/4/23 17:11
 *@Version 1.0.0.0
 */

import lombok.Data;

import java.util.Date;
@Data
public class CartProducTimetDto extends CartProductDto {
    Date cartAddTime;
}
