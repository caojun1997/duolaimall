package com.cskaoyan.user.utils;

import com.cskaoyan.mall.commons.exception.ExceptionUtil;
import com.cskaoyan.mall.commons.result.AbstractResponse;
import com.cskaoyan.user.constants.UserRetCode;

public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException4biz(response,e);
        } catch (Exception ex) {
            response.setCode(UserRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(UserRetCode.SYSTEM_ERROR.getMessage());
        }
    }
}
