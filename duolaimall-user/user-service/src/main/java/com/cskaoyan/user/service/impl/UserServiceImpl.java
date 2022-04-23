package com.cskaoyan.user.service.impl;

import com.cskaoyan.mall.commons.util.jwt.AESUtil;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.converter.UserConverterMapper;
import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dal.persistence.MemberMapper;
import com.cskaoyan.user.dto.UserLoginRequest;
import com.cskaoyan.user.dto.UserLoginResponse;
import com.cskaoyan.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author op027
 * @date 2022年04月23日 10:04
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    UserConverterMapper userConverterMapper;

    @Override
    public UserLoginResponse postLogin(UserLoginRequest request) {
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
        UserLoginResponse responseConverter = userConverterMapper.converter(member);

        String token = JwtTokenUtils.builder().msg(member.getUsername()).build().creatJwtToken();
        responseConverter.setToken(token);
        return responseConverter;
    }
}
