package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author cao jun
 * @description: TODO
 * @date 2022/4/23 9:56
 */
@Service
public class ICartServiceImpI implements ICartService {
    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ItemMapper itemMapper;
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
        Item item = itemMapper.selectByPrimaryKey(request.getItemId());
        CartProducTimetDto cartProducTimetDto = new CartProducTimetDto();
        cartProducTimetDto.setProductId(item.getId());
        cartProducTimetDto.setProductImg(item.getImage());
        cartProducTimetDto.setProductName(item.getTitle());
        cartProducTimetDto.setSalePrice(item.getPrice());
        cartProducTimetDto.setProductNum(request.getNum().longValue());
        Date date = new Date();
        cartProducTimetDto.setCartAddTime(date);


        String userId = request.getUserId().toString();
        RMap<String,CartProducTimetDto> map =redissonClient.getMap(userId);

        // ArrayList<Object> objects = new ArrayList<>(map.values());
        map.put(String.valueOf(item.getId()),cartProducTimetDto);
        map.put(request.getUserId().toString(),cartProducTimetDto);
        AddCartResponse addCartResponse = new AddCartResponse();
        addCartResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        addCartResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        addCartResponse.setAddCartResultItemDtos(cartProducTimetDto);
        return addCartResponse;
    }

    @Override
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        String userId = request.getUserId().toString();
        Long num = request.getNum().longValue();
        RMap<Long,CartProducTimetDto> map =redissonClient.getMap(userId);
        CartProducTimetDto cartProducTimetDto = new CartProducTimetDto();
        Long itemId = request.getItemId();
        cartProducTimetDto = map.get(itemId);
        cartProducTimetDto.setProductNum(num);
        map.put(itemId,cartProducTimetDto);
        UpdateCartNumResponse updateCartNumResponse = new UpdateCartNumResponse();
        updateCartNumResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        updateCartNumResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        updateCartNumResponse.setCartProducTimetDto(cartProducTimetDto);
        return updateCartNumResponse;
    }

    @Override
    public CheckAllItemResponse checkAllCartItem(CheckAllItemRequest request) {
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(DeleteCartItemRequest request) {
        String userId = request.getUserId().toString();
        Long itemId = request.getItemId();
        RMap<Long,CartProducTimetDto> map =redissonClient.getMap(userId);
        map.remove(itemId);
        DeleteCartItemResponse deleteCartItemResponse = new DeleteCartItemResponse();
        deleteCartItemResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        deleteCartItemResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        deleteCartItemResponse.setStatus("成功");
        return deleteCartItemResponse;
    }

    @Override
    public DeleteCheckedItemResposne deleteCheckedItem(DeleteCheckedItemRequest request) {
        String userId = request.getUserId().toString();
        RMap<Long,CartProducTimetDto> map =redissonClient.getMap(userId);
        Iterator<Long> iterator = map.keySet().iterator();
        String key = iterator.next().toString();
        if(map.get(key).getChecked().equals("false")){
            map.remove(key);
        }
        DeleteCheckedItemResposne deleteCheckedItemResposne = new DeleteCheckedItemResposne();
        deleteCheckedItemResposne.setCode(ShoppingRetCode.SUCCESS.getCode());
        deleteCheckedItemResposne.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return deleteCheckedItemResposne;
    }

    @Override
    public ClearCartItemResponse clearCartItemByUserID(ClearCartItemRequest request) {
        return null;
    }


}
