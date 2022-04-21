package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class KaptchaCodeResponse extends AbstractResponse {

    private String imageCode;

    private String uuid;


}
