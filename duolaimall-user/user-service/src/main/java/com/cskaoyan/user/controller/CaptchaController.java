package com.cskaoyan.user.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.commons.util.CookieUtil;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.dto.KaptchaCodeRequest;
import com.cskaoyan.user.dto.KaptchaCodeResponse;
import com.cskaoyan.user.service.IKaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class CaptchaController {

    @Autowired
    IKaptchaService kaptchaService;

    /**
     * 获取验证码
     */
    @GetMapping("/kaptcha")
    public ResponseData getKaptchaCode(HttpServletResponse response) {
        KaptchaCodeRequest kaptchaCodeRequest=new KaptchaCodeRequest();
        KaptchaCodeResponse kaptchaCodeResponse=kaptchaService.getKaptchaCode(kaptchaCodeRequest);
        if(kaptchaCodeResponse.getCode().equals(UserRetCode.SUCCESS.getCode())){
            Cookie cookie= CookieUtil.genCookie("kaptcha_uuid",kaptchaCodeResponse.getUuid(),"/",60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResponseUtil<>().setData(kaptchaCodeResponse.getImageCode());
        }
        return new ResponseUtil<>().setErrorMsg(kaptchaCodeResponse.getCode());
    }

    @PostMapping("/kaptcha")
    public ResponseData validKaptchaCode(@RequestBody String code, HttpServletRequest httpServletRequest) {
        KaptchaCodeRequest request = new KaptchaCodeRequest();
        String uuid = CookieUtil.getCookieValue(httpServletRequest, "kaptcha_uuid");
        request.setUuid(uuid);
        request.setCode(code);
        KaptchaCodeResponse response = kaptchaService.validateKaptchaCode(request);
        if (response.getCode().equals(UserRetCode.SUCCESS.getCode())) {
            return new ResponseUtil<>().setData(null);
        }
        return new ResponseUtil<>().setErrorMsg(response.getCode());
    }
}
