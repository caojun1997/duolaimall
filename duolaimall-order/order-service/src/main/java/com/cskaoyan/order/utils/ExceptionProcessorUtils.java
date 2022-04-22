package com.cskaoyan.order.utils;


import com.cskaoyan.mall.commons.exception.ExceptionUtil;
import com.cskaoyan.mall.commons.result.AbstractResponse;
import com.cskaoyan.mall.order.constant.OrderRetCode;

public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException4biz(response,e);
        } catch (Exception ex) {
            response.setCode(OrderRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(OrderRetCode.SYSTEM_ERROR.getMessage());
        }
    }
}
