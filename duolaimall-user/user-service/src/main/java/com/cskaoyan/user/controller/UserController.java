package com.cskaoyan.user.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.commons.util.CookieUtil;
import com.cskaoyan.user.constants.UserRetCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping("login")
    public ResponseData login(@RequestParam Map<String, String> map, HttpServletRequest request) {
        String access_token = CookieUtil.getCookieValue(request, "access_token");
        if (access_token == null || "".equals(access_token)) {
            return new ResponseUtil<>().setErrorMsg(UserRetCode.TOKEN_VALID_FAILED.getMessage());
        }
        return null;
    }

}
