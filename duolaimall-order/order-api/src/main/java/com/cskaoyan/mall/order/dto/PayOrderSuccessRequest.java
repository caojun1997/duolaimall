package com.cskaoyan.mall.order.dto;

import com.cskaoyan.mall.commons.result.AbstractRequest;
import lombok.Data;

@Data
public class PayOrderSuccessRequest extends AbstractRequest {

    String orderId;

    @Override
    public void requestCheck() {

    }
}
