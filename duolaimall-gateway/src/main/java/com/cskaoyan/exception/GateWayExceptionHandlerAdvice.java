package com.cskaoyan.exception;



import com.cskaoyan.mall.commons.exception.GateWayException;
import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GateWayExceptionHandlerAdvice {

    @ExceptionHandler(value = {GateWayException.class})
    public ResponseData handle(GateWayException ex) {
        log.error("网关异常code:{},msg:{}", ex.getCode(),ex.getMessage());
        return new ResponseUtil().setErrorMsg(ex.getMessage());
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData handle(Throwable throwable) {

        if(throwable instanceof GateWayException) {
            return handle((GateWayException) throwable);
        }else {
            throwable.printStackTrace();
            return new ResponseUtil().setErrorMsg("Internal Server error!");
        }
    }
}
