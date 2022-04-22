package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.user.constants.UserRetCode;
import lombok.Data;


@Data
public class UpdateMemberRequest extends AbstractRequest {
    private static final long serialVersionUID = -2337060153713102164L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 注册手机号
     */
    private String phone;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String file;
    /**
     * 描述
     */
    private String description;
    private Integer points;
    private Long balance;

    private String token;

    @Override
    public void requestCheck() {
        if(null == id){
            throw new ValidateException(
                    UserRetCode.REQUEST_CHECK_FAILURE.getCode(),
                    UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
