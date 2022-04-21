package com.cskaoyan.shopping.dto;



import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import lombok.Data;

@Data
public class CartListByIdRequest extends AbstractRequest {
    private Long userId;
    @Override
    public void requestCheck() {
        if(userId==null||userId.intValue()==0){
            throw new ValidateException(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
