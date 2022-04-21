package com.cskaoyan.order.biz.factory;


import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.order.biz.TransOutboundInvoker;
import com.cskaoyan.order.biz.context.AbsTransHandlerContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.order.biz.convert.TransConvert;
import com.cskaoyan.order.biz.handler.DefaultTransPipeline;
import com.cskaoyan.order.biz.handler.TransPipeline;

public abstract class AbstranctTransPipelineFactory <T extends AbstractRequest> implements TransPipelineFactory<T>{

    @Override
    public final TransOutboundInvoker build(T obj) {

        //创建转换器  CreateOrderConvert
        TransConvert convert = createConvert();

        //创建上下文 创建一个流水线的产品  本质 new CreateOrderContext();
        TransHandlerContext context = createContext();

        //上朔
        AbsTransHandlerContext absCtx = (AbsTransHandlerContext) context;

        //set转换器
        absCtx.setConvert(convert);

        //上下文转换 obj = request
        convert.convertRequest2Ctx(obj, context);

        //创建管道
        TransPipeline pipeline = createPipeline(context);

        //build管道
        doBuild(pipeline);

        //返回
        return pipeline;
    }

    protected abstract TransHandlerContext createContext();

    protected abstract void doBuild(TransPipeline pipeline);

    protected TransPipeline createPipeline(TransHandlerContext context) {
        return new DefaultTransPipeline(context);
    }

    protected abstract TransConvert createConvert();
}
