package com.cskaoyan.shopping.service.impl;/*
 *@Auther:强
 *@Date: 2022/4/22 23:10
 *@Version 1.0.0.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.mall.commons.util.CookieUtil;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.converter.ProductConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.entitys.Member;
import com.cskaoyan.shopping.dal.persistence.ItemCatMapper;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;

import com.cskaoyan.shopping.dal.persistence.MemberMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ICartServiceImpl implements ICartService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ItemCatMapper itemCatMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    ProductConverter productConverter;


    /**
     * @author zwy
     * @date 2022/4/23 11:42
     */

    @Override
    public CartListByIdResponse getCartListById(CartListByIdRequest request) {

        CartListByIdResponse cartListByIdResponse = new CartListByIdResponse();

/*      //参考user模块代码：
        //版本一获取用户ID
        String access_token = CookieUtil.getCookieValue(request, "access_token");
        //校验
        if (access_token == null || "".equals(access_token)) {
            cartListByIdResponse.setMsg(ShoppingRetCode.PARAM_VALIDATE_FAILD.getMessage());
            cartListByIdResponse.setCode(ShoppingRetCode.PARAM_VALIDATE_FAILD.getCode());
            return cartListByIdResponse;
        }
        String freeJwt = null;
        try {
            freeJwt = JwtTokenUtils.builder().token(access_token).build().freeJwt();
        } catch (Exception e) {
            e.printStackTrace();
            cartListByIdResponse.setMsg(ShoppingRetCode.PARAM_VALIDATE_FAILD.getMessage());
            cartListByIdResponse.setCode(ShoppingRetCode.PARAM_VALIDATE_FAILD.getCode());
            return cartListByIdResponse;
        }
        //userServiceImpl中的代码参考
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("username", freeJwt);
        List<Member> members = memberMapper.selectByExample(example);
        Member member = members.get(0);
        Long userId = member.getId();//获取用户ID*/

        request.requestCheck();
        Long userId = request.getUserId();
        //上面获得了当前登录用户的userId
        RMap<String, CartProductTimeDto> map = redissonClient.getMap(String.valueOf(userId));//利用key:userId

        //首先从Map中获取Map中所有的Value(CartProductDto)组成的List
        Collection<CartProductTimeDto> cartProductTimeDtos = map.values();
        List<CartProductTimeDto> timeDtos = new ArrayList<CartProductTimeDto>(cartProductTimeDtos);
        //针对列表进行排序，利用自定义比较器排序

        //自定义排序:
        Collections.sort(timeDtos, new Comparator<CartProductTimeDto>() {
            @Override
            public int compare(CartProductTimeDto o1, CartProductTimeDto o2) {
                return (int) (o1.getCartAddTime().getTime() - o2.getCartAddTime().getTime());
            }
        });

        List<CartProductDto> cartProductDtos = productConverter.cartProductTimeDtos2Dto(timeDtos);

        cartListByIdResponse.setCartProductDtos(cartProductDtos);
        return cartListByIdResponse;
    }

    @Override
    public AddCartResponse addToCart(AddCartRequest request) {
        /*request.requestCheck();
        AddCartResponse addCartResponse = new AddCartResponse();
        Long itemId = request.getItemId();
        Long userId = request.getUserId();
        String productId = Long.toString(itemId);
        RMap<Long,CartProductTimeDto> map =redissonClient.getMap(String.valueOf(userId));
        Iterator<Long> i = map.keySet().iterator();
        boolean productState = false;
        while (i.hasNext()){
            String key = String.valueOf(i.next());
            if(productId.equals(key)){
                productState = true;
            }
        }
        if(map.size() == 0 || !productState){
            Item item = itemMapper.selectByPrimaryKey(itemId);
            if(map.get(productId) == null){
                CartProductTimeDto cartProductTimeDto = new CartProductTimeDto();
                cartProductTimeDto.setProductId(item.getId());
                cartProductTimeDto.setProductImg(item.getImage());
                cartProductTimeDto.setProductName(item.getTitle());
                cartProductTimeDto.setSalePrice(item.getPrice());
                cartProductTimeDto.setProductNum(request.getNum().longValue());
                cartProductTimeDto.setCartAddTime(new Date());
                cartProductTimeDto.setLimitNum(100L);
                cartProductTimeDto.setChecked("true");
                addCartResponse.setCartProductTimeDto(cartProductTimeDto);
            }else{
                map.get(productId).setProductId(item.getId());
                map.get(productId).setProductImg(item.getImage());
                map.get(productId).setProductName(item.getTitle());
                map.get(productId).setSalePrice(item.getPrice());
                map.get(productId).setProductNum(request.getNum().longValue());
                map.get(productId).setCartAddTime(new Date());
                map.get(productId).setLimitNum(100L);
                map.get(productId).setChecked("true");
                addCartResponse.setCartProductTimeDto(map.get(productId));
            }

        }else{
            CartProductTimeDto dto = map.get(productId);
            Long productNum = dto.getProductNum();
            productNum = productNum + 1;
            dto.setProductNum(productNum);
        }

        addCartResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        addCartResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return addCartResponse;*/
        Item item = itemMapper.selectByPrimaryKey(request.getItemId());
        CartProductTimeDto cartProductTimetDto = new CartProductTimeDto();
        cartProductTimetDto.setProductId(item.getId());
        cartProductTimetDto.setProductImg(item.getImage());
        cartProductTimetDto.setProductName(item.getTitle());
        cartProductTimetDto.setSalePrice(item.getPrice());
        cartProductTimetDto.setProductNum(request.getNum().longValue());
        Date date = new Date();
        cartProductTimetDto.setCartAddTime(date);
        cartProductTimetDto.setLimitNum(100L);
        cartProductTimetDto.setChecked("true");


        String userId = request.getUserId().toString();
        RMap<String,CartProductTimeDto> map =redissonClient.getMap(userId);

        // ArrayList<Object> objects = new ArrayList<>(map.values());
        map.put(String.valueOf(item.getId()),cartProductTimetDto);
        map.put(request.getUserId().toString(),cartProductTimetDto);
        AddCartResponse addCartResponse = new AddCartResponse();
        addCartResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        addCartResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        addCartResponse.setAddCartResultItemDtos(cartProductTimetDto);
        return addCartResponse;
    }

    @Override
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        request.requestCheck();
        String userId = request.getUserId().toString();
        Long num = request.getNum().longValue();
        RMap<Long,CartProductTimeDto> map =redissonClient.getMap(userId);
        CartProductTimeDto cartProducTimetDto;
        Long itemId = request.getItemId();
        cartProducTimetDto = map.get(itemId + "");
        cartProducTimetDto.setProductNum(num);
        map.put(itemId,cartProducTimetDto);
        UpdateCartNumResponse updateCartNumResponse = new UpdateCartNumResponse();
        updateCartNumResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        updateCartNumResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        updateCartNumResponse.setCartProductTimeDto(cartProducTimetDto);
        return updateCartNumResponse;
    }

    @Override
    public CheckAllItemResponse checkAllCartItem(CheckAllItemRequest request) {
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(DeleteCartItemRequest request) {
        request.requestCheck();
        String userId = request.getUserId().toString();
        Long itemId = request.getItemId();
        String productId = Long.toString(itemId);
        RMap<Long,CartProductTimeDto> map =redissonClient.getMap(userId);
        Iterator<Long> i = map.keySet().iterator();
        while (i.hasNext()){
            String key = String.valueOf(i.next());
            if(productId.equals(key)){
                i.remove();
            }
        }
        DeleteCartItemResponse deleteCartItemResponse = new DeleteCartItemResponse();
        deleteCartItemResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        deleteCartItemResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        deleteCartItemResponse.setStatus("成功");
        return deleteCartItemResponse;
    }

    @Override
    public DeleteCheckedItemResposne deleteCheckedItem(DeleteCheckedItemRequest request) {
        request.requestCheck();
        String userId = request.getUserId().toString();
        RMap<Long,CartProductTimeDto> map =redissonClient.getMap(userId);
        Iterator<Long> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key = String.valueOf(iterator.next());
            if("false".equals(map.get(key).getChecked())){
                iterator.remove();
            }
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
