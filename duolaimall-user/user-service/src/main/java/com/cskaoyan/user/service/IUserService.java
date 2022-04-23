package com.cskaoyan.user.service;

import com.cskaoyan.user.dto.UserLoginRequest;
import com.cskaoyan.user.dto.UserLoginResponse;

public interface IUserService {

    UserLoginResponse postLogin(UserLoginRequest request);


}
