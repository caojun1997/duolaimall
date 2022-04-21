package com.cskaoyan.order.biz.handler;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.mall.commons.exception.BaseBusinessException;
import com.cskaoyan.order.biz.context.CreateOrderContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.order.dal.entitys.Stock;
import com.cskaoyan.order.dal.persistence.StockMapper;
import com.cskaoyan.order.dto.CartProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 扣减库存处理器
 **/
@Component
@Slf4j
public class SubStockHandler extends AbstractTransHandler {


    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    @Transactional
    public boolean handle(TransHandlerContext context) {
        return true;
    }
}