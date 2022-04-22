package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.user.constants.UserRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class KaptchaCodeRequest extends AbstractRequest {

    private String uuid;

    private String code;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(uuid)|| StringUtils.isBlank(code)){
            throw new ValidateException(
                    UserRetCode.REQUEST_CHECK_FAILURE.getCode(),
                    UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
