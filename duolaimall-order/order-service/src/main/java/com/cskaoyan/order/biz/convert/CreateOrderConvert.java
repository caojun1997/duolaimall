package com.cskaoyan.order.biz.convert;

import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.commons.result.AbstractResponse;
import com.cskaoyan.order.biz.context.CreateOrderContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import com.cskaoyan.order.dto.CreateOrderRequest;
import com.cskaoyan.order.dto.CreateOrderResponse;

public class CreateOrderConvert implements TransConvert {


    // 把我们的request转化为 context
    @Override
    public TransHandlerContext convertRequest2Ctx(AbstractRequest req, TransHandlerContext context) {
        CreateOrderRequest createOrderRequest = (CreateOrderRequest) req;
        CreateOrderContext createOrderContext = (CreateOrderContext) context;

        // 将request中的请求内容，放入创建好的上下文中
        createOrderContext.setAddressId(createOrderRequest.getAddressId());
        createOrderContext.setCartProductDtoList(createOrderRequest.getCartProductDtoList());
        createOrderContext.setOrderTotal(createOrderRequest.getOrderTotal());
        createOrderContext.setStreetName(createOrderRequest.getStreetName());
        createOrderContext.setTel(createOrderRequest.getTel());
        createOrderContext.setUserId(createOrderRequest.getUserId());
        createOrderContext.setUserName(createOrderRequest.getUserName());
        createOrderContext.setUniqueKey(createOrderRequest.getUniqueKey());
        return createOrderContext;
    }

    // 把我们经过管道模式处理的结果转化为response
    @Override
    public AbstractResponse convertCtx2Respond(TransHandlerContext ctx) {
        CreateOrderContext createOrderContext = (CreateOrderContext) ctx;
        CreateOrderResponse createOrderResponse = new CreateOrderResponse();
        createOrderResponse.setOrderId(createOrderContext.getOrderId());
        createOrderResponse.setCode(OrderRetCode.SUCCESS.getCode());
        createOrderResponse.setMsg(OrderRetCode.SUCCESS.getMessage());
        return createOrderResponse;
    }
}
