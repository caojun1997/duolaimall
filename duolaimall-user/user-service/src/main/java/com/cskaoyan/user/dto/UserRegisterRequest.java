package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.user.constants.UserRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class UserRegisterRequest extends AbstractRequest {

    private String userName;
    private String userPwd;
    private String email;

//    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(userName)|| StringUtils.isBlank(userPwd)){
            throw new ValidateException(UserRetCode.REQUEST_CHECK_FAILURE.getCode(), UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
