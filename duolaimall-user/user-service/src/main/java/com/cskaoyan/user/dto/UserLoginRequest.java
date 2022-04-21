package com.cskaoyan.user.dto;


import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.user.constants.UserRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class UserLoginRequest extends AbstractRequest {
    private String userName;
    private String password;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(userName)|| StringUtils.isBlank(password)){
            throw new ValidateException(
                    UserRetCode.REQUEST_CHECK_FAILURE.getCode(),
                    UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
