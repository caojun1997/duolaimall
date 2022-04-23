package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cao jun
 * @description: TODO
 * @date 2022/4/23 9:56
 */
@Service
public class ICartServiceImp implements ICartService {
    @Autowired
    RedissonClient redissonClient;

    // 获得购物车商品列表
    @Override
    public CartListByIdResponse getCartListById(CartListByIdRequest request) {
        try {
            request.requestCheck();
            RMap<String, CartProductDto> map = redissonClient.getMap(String.valueOf(request.getUserId()));
            List<CartProductDto> cartProductDtoList = new ArrayList<CartProductDto>();
            for (String itemId : map.keySet()) {
                cartProductDtoList.add(map.get(itemId));
            }
        } catch (Exception e) {
            // 这里可能有用户信息错误
            CartListByIdResponse cartListByIdResponse = new CartListByIdResponse();
            cartListByIdResponse.setCode(ShoppingRetCode.PARAM_VALIDATE_FAILD.getCode());
            cartListByIdResponse.setMsg(ShoppingRetCode.PARAM_VALIDATE_FAILD.getMessage());
        }


        return null;
    }

    @Override
    public AddCartResponse addToCart(AddCartRequest request) {
        return null;
    }

    @Override
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        return null;
    }

    @Override
    public CheckAllItemResponse checkAllCartItem(CheckAllItemRequest request) {
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(DeleteCartItemRequest request) {
        return null;
    }

    @Override
    public DeleteCheckedItemResposne deleteCheckedItem(DeleteCheckedItemRequest request) {
        return null;
    }

    @Override
    public ClearCartItemResponse clearCartItemByUserID(ClearCartItemRequest request) {
        return null;
    }


}
