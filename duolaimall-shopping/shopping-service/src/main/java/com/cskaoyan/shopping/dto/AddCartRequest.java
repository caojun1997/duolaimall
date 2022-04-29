package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import lombok.Data;


@Data
public class AddCartRequest extends AbstractRequest {

    private Long userId;
    private Long itemId;
    private Integer num;
    private CartProductTimeDto cartProductTimeDto;

    @Override
    public void requestCheck() {
        if(userId == null || userId < 0 || itemId == null || itemId < 0 || num == null | num < 0){
            throw new ValidateException(ShoppingRetCode.PARAM_VALIDATE_FAILD.getCode(),
                    ShoppingRetCode.PARAM_VALIDATE_FAILD.getMessage());
        }
    }
}
