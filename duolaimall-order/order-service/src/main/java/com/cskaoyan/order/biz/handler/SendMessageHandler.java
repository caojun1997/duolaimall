package com.cskaoyan.order.biz.handler;

import com.cskaoyan.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: 利用mq发送延迟取消订单消息
 **/
@Component
@Slf4j
public class SendMessageHandler extends AbstractTransHandler {



	@Override
	public boolean isAsync() {
		return false;
	}

	@Override
	public boolean handle(TransHandlerContext context) {
		return true;
	}
}