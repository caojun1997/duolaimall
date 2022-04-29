package com.cskaoyan.shopping.ParamBean;/*
 *@Auther:强
 *@Date: 2022/4/29 21:27
 *@Version 1.0.0.0
 */

import lombok.Data;

@Data
public class UpdateBean {
    private String checked;
    private Long productId;
    private Integer productNum;
    private Long userId;
}
