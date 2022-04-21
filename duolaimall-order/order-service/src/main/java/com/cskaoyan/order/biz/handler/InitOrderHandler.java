package com.cskaoyan.order.biz.handler;

import com.cskaoyan.mall.commons.exception.BizException;
import com.cskaoyan.mall.commons.util.NumberUtils;
import com.cskaoyan.order.biz.callback.SendEmailCallback;
import com.cskaoyan.order.biz.callback.TransCallback;
import com.cskaoyan.order.biz.context.CreateOrderContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.order.constant.OrderConstants;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.persistence.OrderItemMapper;
import com.cskaoyan.order.dal.persistence.OrderMapper;
import com.cskaoyan.order.utils.GlobalIdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 初始化订单 生成订单
 */

@Slf4j
@Component
public class InitOrderHandler extends AbstractTransHandler {


    @Autowired
    SendEmailCallback sendEmailCallback;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    GlobalIdGeneratorUtil globalIdGeneratorUtil;



    private final String ORDER_GLOBAL_ID_CACHE_KEY="ORDER_ID";
    private final String ORDER_ITEM_GLOBAL_ID_CACHE_KEY="ORDER_ITEM_ID";
    @Override
    public TransCallback getTransCallback() {
        return sendEmailCallback;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        return true;
    }

}
