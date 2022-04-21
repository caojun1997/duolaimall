package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractRequest;
import lombok.Data;


@Data
public class AddCartRequest extends AbstractRequest {

    private Long userId;
    private Long itemId;
    private Integer num;

    @Override
    public void requestCheck() {

    }
}
