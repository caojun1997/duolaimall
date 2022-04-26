package com.cskaoyan.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.mall.commons.util.CookieUtil;
import com.cskaoyan.mall.commons.util.jwt.AESUtil;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.converter.UserConverterMapper;
import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dal.entitys.UserVerify;
import com.cskaoyan.user.dal.persistence.MemberMapper;
import com.cskaoyan.user.dal.persistence.UserVerifyMapper;
import com.cskaoyan.user.dto.UserLoginRequest;
import com.cskaoyan.user.dto.UserLoginResponse;
import com.cskaoyan.user.dto.UserRegisterRequest;
import com.cskaoyan.user.dto.UserRegisterResponse;
import com.cskaoyan.user.dto.UserVerifyRequest;
import com.cskaoyan.user.dto.UserVerifyResponse;
import com.cskaoyan.user.service.IUserService;
import com.cskaoyan.user.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author op027
 * @date 2022年04月23日 10:04
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    UserVerifyMapper userVerifyMapper;
    @Autowired
    UserConverterMapper userConverterMapper;
    @Autowired
    JavaMailSender mailSender;

    @Override
    public UserLoginResponse postLogin(UserLoginRequest request, HttpServletResponse response) {
        request.requestCheck();

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        String password = request.getPassword();
        AESUtil aesUtil = new AESUtil(password);
        // 使用加密算法得到加密后的字符串
        String encrypt = aesUtil.encrypt();

        // 验证数据库中是否存在对应的用户名和密码
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("username", request.getUserName()).andEqualTo("password", encrypt);
        List<Member> members = memberMapper.selectByExample(example);

        if (members == null || members.isEmpty()) {
            userLoginResponse.setCode(UserRetCode.USERORPASSWORD_ERRROR.getCode());
            userLoginResponse.setMsg(UserRetCode.USERORPASSWORD_ERRROR.getMessage());
            return userLoginResponse;
        }
        Member member = members.get(0);
        if ("N".equals(member.getIsVerified())) {
            userLoginResponse.setCode(UserRetCode.USER_ISVERFIED_ERROR.getCode());
            userLoginResponse.setMsg(UserRetCode.USER_ISVERFIED_ERROR.getMessage());
            return userLoginResponse;
        }
        UserLoginResponse responseConverter = userConverterMapper.converter(member);
        String str = JSON.toJSON(member).toString();
        String token = JwtTokenUtils.builder().msg(str).build().creatJwtToken();

        Cookie cookie = CookieUtil.genCookie("access_token", token, "/", 600);
        response.addCookie(cookie);

        responseConverter.setToken(token);
        responseConverter.setCode(UserRetCode.SUCCESS.getCode());
        responseConverter.setMsg(UserRetCode.SUCCESS.getMessage());
        return responseConverter;
    }

    @Override
    public UserLoginVO getLogin(String freeJwt) {

        JSONObject jsonObject = JSON.parseObject(freeJwt);
        Long id = Long.parseLong(jsonObject.get("id").toString());
        String username = jsonObject.get("username").toString();
        return new UserLoginVO(id, username);
    }

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        userRegisterRequest.requestCheck();
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        // 1.验证邮箱是否重复
        String email = userRegisterRequest.getEmail();
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("email", email);
        List<Member> members = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(members)) {
            userRegisterResponse.setCode(UserRetCode.EMAIL_ALREADY_EXISTS.getCode());
            userRegisterResponse.setMsg(UserRetCode.EMAIL_ALREADY_EXISTS.getMessage());
            return userRegisterResponse;
        }

        // 2.验证用户名是否重复

        String userName = userRegisterRequest.getUserName();
        Example example2 = new Example(Member.class);
        example2.createCriteria().andEqualTo("username", userName);
        List<Member> members2 = memberMapper.selectByExample(example2);
        if (!CollectionUtils.isEmpty(members2)) {
            userRegisterResponse.setCode(UserRetCode.USERNAME_ALREADY_EXISTS.getCode());
            userRegisterResponse.setMsg(UserRetCode.USERNAME_ALREADY_EXISTS.getMessage());
            return userRegisterResponse;
        }

        // 3.向用户表中插入一条记录
        Member member;
        try {
            member = new Member();
            member.setUsername(userRegisterRequest.getUserName());
            AESUtil aesUtil = new AESUtil(userRegisterRequest.getUserPwd());
            String encrypt = aesUtil.encrypt();
            member.setPassword(encrypt);
            member.setEmail(userRegisterRequest.getEmail());
            member.setCreated(new Date());
            member.setUpdated(new Date());
            int num1 = memberMapper.insertSelective(member);
        } catch (Exception e) {
            e.printStackTrace();
            userRegisterResponse.setCode(UserRetCode.USER_REGISTER_VERIFY_FAILED.getCode());
            userRegisterResponse.setMsg(UserRetCode.USER_REGISTER_VERIFY_FAILED.getMessage());
            return userRegisterResponse;
        }

        // 4.向用户验证表中插入一条记录
        UserVerify userVerify;
        try {
            userVerify = new UserVerify();
            userVerify.setUsername(userRegisterRequest.getUserName());
            userVerify.setRegisterDate(new Date());
            userVerify.setIsVerify("N");
            userVerify.setIsExpire("N");
            String uuid = UUID.randomUUID().toString();
            userVerify.setUuid(uuid);
            int num2 = userVerifyMapper.insertSelective(userVerify);
        } catch (Exception e) {
            e.printStackTrace();
            userRegisterResponse.setCode(UserRetCode.USER_REGISTER_VERIFY_FAILED.getCode());
            userRegisterResponse.setMsg(UserRetCode.USER_REGISTER_VERIFY_FAILED.getMessage());
            return userRegisterResponse;
        }

        // 5.发送激活邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("激活邮件");
        String url = "http://localhost:9999" + "/user/verify?uid=" + userVerify.getUuid() + "&username="
            + userRegisterRequest.getUserName();
        simpleMailMessage.setText(url);
        simpleMailMessage.setFrom("op027@qq.com");
        simpleMailMessage.setTo(member.getEmail());

        mailSender.send(simpleMailMessage);

        userRegisterResponse.setCode(UserRetCode.SUCCESS.getCode());
        userRegisterResponse.setMsg(UserRetCode.SUCCESS.getMessage());
        return userRegisterResponse;
    }

    @Override
    public UserVerifyResponse verifyUser(UserVerifyRequest userVerifyRequest) {

        userVerifyRequest.requestCheck();

        UserVerifyResponse userVerifyResponse = new UserVerifyResponse();

        Example example = new Example(UserVerify.class);
        example.createCriteria().andEqualTo("uuid", userVerifyRequest.getUuid());
        UserVerify userVerify = new UserVerify();
        userVerify.setIsVerify("Y");
        int i = userVerifyMapper.updateByExampleSelective(userVerify, example);
        if (i != 1) {
            userVerifyResponse.setCode(UserRetCode.USERVERIFY_INFOR_INVALID.getCode());
            userVerifyResponse.setMsg(UserRetCode.USERVERIFY_INFOR_INVALID.getMessage());
            return userVerifyResponse;
        }
        Example example2 = new Example(Member.class);
        example2.createCriteria().andEqualTo("username", userVerifyRequest.getUserName());
        Member member = new Member();
        member.setIsVerified("Y");
        int i1 = memberMapper.updateByExampleSelective(member, example2);
        if (i1 != 1) {
            userVerifyResponse.setCode(UserRetCode.USERVERIFY_INFOR_INVALID.getCode());
            userVerifyResponse.setMsg(UserRetCode.USERVERIFY_INFOR_INVALID.getMessage());
            return userVerifyResponse;
        }
        userVerifyResponse.setCode(UserRetCode.SUCCESS.getCode());
        userVerifyResponse.setMsg(UserRetCode.SUCCESS.getMessage());
        return userVerifyResponse;
    }
}
