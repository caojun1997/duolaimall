package com.cskaoyan.order.biz.context;

import com.cskaoyan.order.dto.CartProductDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderContext extends AbsTransHandlerContext{

    private Long userId;

    private Long addressId;

    private String tel;

    private String userName;

    private String streetName;

    private BigDecimal orderTotal;

    List<CartProductDto> cartProductDtoList;

    private List<Long> buyProductIds;  // 生成订单商品的ID list

    private String buyerNickName;  // 购买用户的昵称

    private String uniqueKey; //业务唯一id

}
