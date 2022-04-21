package com.cskaoyan.mall.commons.exception;


import lombok.Data;

@Data
public class GateWayException extends RuntimeException {

    private String code;

    private String message;

    public GateWayException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
