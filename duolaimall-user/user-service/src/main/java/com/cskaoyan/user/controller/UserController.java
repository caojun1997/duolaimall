package com.cskaoyan.user.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.commons.util.CookieUtil;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.dto.KaptchaCodeRequest;
import com.cskaoyan.user.dto.KaptchaCodeResponse;
import com.cskaoyan.user.dto.UserLoginRequest;
import com.cskaoyan.user.dto.UserLoginResponse;
import com.cskaoyan.user.service.IKaptchaService;
import com.cskaoyan.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author op027
 * @date 2022年04月22日 21:43
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    IKaptchaService kaptchaService;

    @GetMapping("login")
    public ResponseData getLogin(HttpServletRequest request) {
        String access_token = CookieUtil.getCookieValue(request, "access_token");
        if (access_token == null || "".equals(access_token)) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.TOKEN_VALID_FAILED.getMessage());
        }
        String freeJwt = JwtTokenUtils.builder().token(access_token).build().freeJwt();

        return null;
    }

    @PostMapping("login")
    public ResponseData postLogin(@RequestBody Map<String, String> map, HttpServletRequest request) {
        // 首先校验验证码是否正确
        KaptchaCodeResponse response = getKaptchaCodeResponse(map, request);
        // 如果错误直接返回错误信息
        if (!UserRetCode.SUCCESS.getCode().equals(response.getCode())) {
            return new ResponseUtil<>().setErrorMsg("验证码输入错误");
        }

        // 验证用户名、密码是否正确
        String userName = map.get("userName");
        String userPwd = map.get("userPwd");
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName(userName);
        userLoginRequest.setPassword(userPwd);
        UserLoginResponse userLoginResponse = userService.postLogin(userLoginRequest);
        return new ResponseUtil<>().setData(userLoginResponse);
    }

    private KaptchaCodeResponse getKaptchaCodeResponse(Map<String, String> map, HttpServletRequest request) {
        KaptchaCodeRequest kaptchaRequest = new KaptchaCodeRequest();
        String uuid = CookieUtil.getCookieValue(request, "kaptcha_uuid");
        String captcha = map.get("captcha");
        kaptchaRequest.setUuid(uuid);
        kaptchaRequest.setCode(captcha);
        KaptchaCodeResponse response = kaptchaService.validateKaptchaCode(kaptchaRequest);
        return response;
    }

}
