package com.cskaoyan.mall.api;

import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CartService {

    /**
     * 清空指定用户的购物车缓存(用户下完订单之后清理）
     * @param request
     * @return
     */
    @RequestMapping(value = "/rpc/items",method = RequestMethod.DELETE)
    ClearCartItemResponse clearCartItemByUserID(@RequestBody ClearCartItemRequest request);

}
