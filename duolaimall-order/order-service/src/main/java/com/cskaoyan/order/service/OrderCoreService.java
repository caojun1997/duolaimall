package com.cskaoyan.order.service;


import com.cskaoyan.mall.order.dto.PayOrderSuccessRequest;
import com.cskaoyan.mall.order.dto.PayOrderSuccessResponse;
import com.cskaoyan.order.dto.*;

/**
 * 订单相关业务
 */
public interface OrderCoreService {

    /**
     * 创建订单
     * @param request
     * @return
     */
    CreateOrderResponse createOrder(CreateOrderRequest request);

    /**
     * 取消订单
     * @param request
     * @return
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest request);


    /**
     * 删除订单
     * @param request
     * @return
     */
    DeleteOrderResponse deleteOrder(DeleteOrderRequest request);

    /**
     * 修改订单状态
     * @param status
     * @param orderId
     */
    void updateOrder(Integer status, String orderId);

    /**
     * 删除订单与交易
     * @param request
     */
    void deleteOrderWithTransaction(DeleteOrderRequest request);


    /**
     *  支付成功时，更新订单状态，更新锁定库存，更新订单商品库存状态
     * @param request
     * @return
     */
    PayOrderSuccessResponse payOrderSuccess(PayOrderSuccessRequest request);



}
