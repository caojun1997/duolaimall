package com.cskaoyan.order.dal.persistence;


import com.cskaoyan.order.dal.entitys.Order;
import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order> {
    Long countAll();
}