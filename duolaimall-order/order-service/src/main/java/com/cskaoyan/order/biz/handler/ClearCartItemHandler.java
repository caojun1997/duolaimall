package com.cskaoyan.order.biz.handler;

import com.cskaoyan.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ClearCartItemHandler extends AbstractTransHandler {




    //是否采用异步方式执行
    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        return true;
    }
}
