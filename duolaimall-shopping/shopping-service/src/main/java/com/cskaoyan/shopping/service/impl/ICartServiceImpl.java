package com.cskaoyan.shopping.service.impl;/*
 *@Auther:强
 *@Date: 2022/4/22 23:10
 *@Version 1.0.0.0
 */

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.converter.ProductConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.entitys.ItemCat;
import com.cskaoyan.shopping.dal.persistence.ItemCatMapper;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ICartServiceImpl implements ICartService {

    @@Autowired
    RedissonClient redissonClient;

    @Autowired
    ItemCatMapper itemCatMapper;

    @Autowired
    ItemMapper itemMapper;

    @Override
    public CartListByIdResponse getCartListById(CartListByIdRequest request) {
        return null;
    }

    @Override
    public AddCartResponse addToCart(AddCartRequest request) {
        Item item = itemMapper.selectByPrimaryKey(request.getItemId());
        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setProductId(item.getId());
        cartProductDto.setProductImg(item.getImage());
        cartProductDto.setProductName(item.getTitle());
        cartProductDto.setSalePrice(item.getPrice());
        cartProductDto.setProductNum(request.getNum().longValue());
        //HashMap<Long, CartProductDto> longCartProductDtoHashMap = new HashMap<Long, CartProductDto>();
        //longCartProductDtoHashMap.put(request.getUserId(),cartProductDto);


        RMap<String,CartProductDto> map =redissonClient.getMap("SuiBian");

        map.put(String.valueOf(item.getId()),cartProductDto);
        map.put(request.getUserId().toString(),cartProductDto);
        AddCartResponse addCartResponse = new AddCartResponse();
        addCartResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        addCartResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return addCartResponse;
    }

    @Override
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        String userId = request.getUserId().toString();
        Long num = request.getNum().longValue();
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.7.4:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RMap<Long,CartProductDto> map =redissonClient.getMap(userId);
        map.put
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
