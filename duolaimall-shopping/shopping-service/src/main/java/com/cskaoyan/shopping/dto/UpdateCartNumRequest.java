package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class UpdateCartNumRequest extends AbstractRequest {
    private Long userId;
    private Long itemId;
    private Integer num;
    private String checked;

    @Override
    public void requestCheck() {
        if(userId==null||itemId==null||num==null|| StringUtils.isBlank(checked)){
            throw new ValidateException(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
