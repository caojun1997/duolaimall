
package com.cskaoyan.order.biz.convert;


import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.commons.result.AbstractResponse;
import com.cskaoyan.order.biz.context.TransHandlerContext;

public interface TransConvert {
    /**
     * 请求转上下文
     * 
     * @param req
     * @return
     */
    TransHandlerContext convertRequest2Ctx(AbstractRequest req, TransHandlerContext context);
    
    /**
     * 上下文转应答
     * 
     * @param ctx
     * @return
     */
    AbstractResponse convertCtx2Respond(TransHandlerContext ctx);
}
