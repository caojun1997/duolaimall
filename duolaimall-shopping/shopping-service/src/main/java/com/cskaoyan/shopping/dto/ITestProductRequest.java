package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import lombok.Data;

@Data
public class ITestProductRequest extends AbstractRequest {

    Long productId;

    @Override
    public void requestCheck() {
        if (productId == null || productId <= 0) {
            throw new ValidateException(ShoppingRetCode.PARAM_VALIDATE_FAILD.getCode(),
                    ShoppingRetCode.PARAM_VALIDATE_FAILD.getMessage());
        }
    }
}
