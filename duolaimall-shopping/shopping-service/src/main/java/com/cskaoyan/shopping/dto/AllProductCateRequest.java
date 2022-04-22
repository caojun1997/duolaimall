package com.cskaoyan.shopping.dto;


import com.cskaoyan.mall.commons.result.AbstractRequest;
import lombok.Data;

@Data
public class AllProductCateRequest extends AbstractRequest {
    private String sort;

    @Override
    public void requestCheck() {

    }
}
