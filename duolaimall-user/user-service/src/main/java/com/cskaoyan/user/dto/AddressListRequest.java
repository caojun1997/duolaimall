package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.user.constants.UserRetCode;
import lombok.Data;

@Data
public class AddressListRequest extends AbstractRequest {
    private Long userId;

    @Override
    public void requestCheck() {
        if(userId==null){
            throw new ValidateException(
                    UserRetCode.REQUEST_CHECK_FAILURE.getCode(),
                    UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
