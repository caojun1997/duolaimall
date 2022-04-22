package com.cskaoyan.shopping.form;

import lombok.Data;


@Data
public class CartForm {

    private Long userId;

    private Long productId;

    private String checked;

    private Integer productNum;


}
