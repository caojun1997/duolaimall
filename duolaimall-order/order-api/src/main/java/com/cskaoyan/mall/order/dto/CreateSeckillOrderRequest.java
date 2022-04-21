package com.cskaoyan.mall.order.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Data
public class CreateSeckillOrderRequest extends AbstractRequest {
    private Long userId;
    private String username;
    private Long productId;
    private BigDecimal price;
    private Long addressId;

    private String tel;

    private String streetName;
    @Override
    public void requestCheck() {

//        if (userId == null || StringUtils.isBlank(username) || productId == null || price == null) {
//            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
//        }
        throw new RuntimeException("测试seata");

    }
}