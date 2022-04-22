package com.cskaoyan.order.biz.factory;

import com.cskaoyan.order.biz.TransOutboundInvoker;

public interface TransPipelineFactory<T> {

    TransOutboundInvoker build(T obj);
}
