package com.cskaoyan.mall.order.service;

import com.cskaoyan.mall.order.dto.PayOrderSuccessRequest;
import com.cskaoyan.mall.order.dto.PayOrderSuccessResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface OrderPayService {

    /**
     *  支付成功时，更新订单状态，更新锁定库存，更新订单商品库存状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/rpc/pay/success", method = RequestMethod.PUT)
    PayOrderSuccessResponse payOrderSuccess(@RequestBody PayOrderSuccessRequest request);
}
