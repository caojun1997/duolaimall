package com.cskaoyan.order.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class CreateOrderResponse extends AbstractResponse {

    private String orderId;
}
