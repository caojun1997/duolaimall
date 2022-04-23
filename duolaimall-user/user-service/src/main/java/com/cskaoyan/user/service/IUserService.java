package com.cskaoyan.user.service;

import com.cskaoyan.user.dto.UserLoginRequest;
import com.cskaoyan.user.dto.UserLoginResponse;
import com.cskaoyan.user.dto.UserRegisterRequest;
import com.cskaoyan.user.dto.UserRegisterResponse;
import com.cskaoyan.user.dto.UserVerifyRequest;
import com.cskaoyan.user.dto.UserVerifyResponse;
import com.cskaoyan.user.vo.UserLoginVO;

import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    UserLoginResponse postLogin(UserLoginRequest request, HttpServletResponse response);


    UserLoginVO getLogin(String freeJwt);

    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);

    UserVerifyResponse verifyUser(UserVerifyRequest userVerifyRequest);
}
