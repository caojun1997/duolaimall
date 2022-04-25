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
import com.cskaoyan.user.dto.UserRegisterRequest;
import com.cskaoyan.user.dto.UserRegisterResponse;
import com.cskaoyan.user.dto.UserVerifyRequest;
import com.cskaoyan.user.dto.UserVerifyResponse;
import com.cskaoyan.user.service.IKaptchaService;
import com.cskaoyan.user.service.IUserService;
import com.cskaoyan.user.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String freeJwt = null;
        try {
            freeJwt = JwtTokenUtils.builder().token(access_token).build().freeJwt();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil<>().setErrorMsg(UserRetCode.TOKEN_VALID_FAILED.getMessage());
        }

        UserLoginVO login = userService.getLogin(freeJwt);

        return new ResponseUtil<>().setData(login);
    }

    @PostMapping("login")
    public ResponseData postLogin(@RequestBody Map<String, String> map, HttpServletRequest request,
        HttpServletResponse httpServletResponse) {
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
        UserLoginResponse userLoginResponse = userService.postLogin(userLoginRequest, httpServletResponse);
        if (UserRetCode.USERORPASSWORD_ERRROR.getCode().equals(userLoginResponse.getCode())) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.USERORPASSWORD_ERRROR.getMessage());
        }
        if (UserRetCode.USER_ISVERFIED_ERROR.getCode().equals(userLoginResponse.getCode())) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.USER_ISVERFIED_ERROR.getMessage());
        }

        return new ResponseUtil<>().setData(userLoginResponse);
    }

    @RequestMapping("register")
    public ResponseData registerUser(@RequestBody Map<String, String> map, HttpServletRequest request) {
        // 首先校验验证码是否正确
        KaptchaCodeResponse response = getKaptchaCodeResponse(map, request);
        // 如果错误直接返回错误信息
        if (!UserRetCode.SUCCESS.getCode().equals(response.getCode())) {
            return new ResponseUtil<>().setErrorMsg("验证码输入错误");
        }

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName(map.get("userName"));
        userRegisterRequest.setUserPwd(map.get("userPwd"));
        userRegisterRequest.setEmail(map.get("email"));

        UserRegisterResponse registerResponse = userService.registerUser(userRegisterRequest);

        if (UserRetCode.EMAIL_ALREADY_EXISTS.getCode().equals(registerResponse.getCode())) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        if (UserRetCode.USERNAME_ALREADY_EXISTS.getCode().equals(registerResponse.getCode())) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.USERNAME_ALREADY_EXISTS.getMessage());
        }
        if (UserRetCode.USER_REGISTER_VERIFY_FAILED.getCode().equals(registerResponse.getCode())) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.USER_REGISTER_VERIFY_FAILED.getMessage());
        }
        return new ResponseUtil<>().setData(registerResponse);
    }

    @RequestMapping("loginOut")
    public ResponseData logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("access_token".equals(cookie.getName())) {
                Cookie genCookie = CookieUtil.genCookie("access_token", null, "/", 0);
                response.addCookie(genCookie);
            }
        }
        ResponseUtil responseUtil = new ResponseUtil();
        return new ResponseUtil<>().setData(null);
    }

    @RequestMapping("verify")
    public ResponseData verify(@RequestParam Map<String, String> map) {

        String uid = map.get("uid");
        String username = map.get("username");
        UserVerifyRequest userVerifyRequest = new UserVerifyRequest();
        userVerifyRequest.setUserName(username);
        userVerifyRequest.setUuid(uid);

        UserVerifyResponse userVerifyResponse = userService.verifyUser(userVerifyRequest);
        return new ResponseUtil<>().setData(userVerifyResponse);
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
