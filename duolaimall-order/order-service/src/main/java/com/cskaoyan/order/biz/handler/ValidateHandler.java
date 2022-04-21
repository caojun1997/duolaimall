package com.cskaoyan.order.biz.handler;

import com.cskaoyan.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateHandler extends AbstractTransHandler {


    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        return true;
    }
}
