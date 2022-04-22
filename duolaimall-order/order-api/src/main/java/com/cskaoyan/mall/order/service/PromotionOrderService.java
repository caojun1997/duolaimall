package com.cskaoyan.mall.order.service;

import com.cskaoyan.mall.order.dto.CreateSeckillOrderRequest;
import com.cskaoyan.mall.order.dto.CreateSeckillOrderResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface PromotionOrderService {

    @RequestMapping(value = "/rpc/promotion/order",method = RequestMethod.POST)
    CreateSeckillOrderResponse createPromoOrder(CreateSeckillOrderRequest request);
}
