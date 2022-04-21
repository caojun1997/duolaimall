package com.cskaoyan.order.biz.callback;


import com.cskaoyan.order.biz.context.TransHandlerContext;

/**
 * 交易回调
 */
public interface TransCallback {

    void onDone(TransHandlerContext context);
}
