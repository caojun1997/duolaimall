package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class CheckAuthResponse extends AbstractResponse {

    private String userinfo;
}
