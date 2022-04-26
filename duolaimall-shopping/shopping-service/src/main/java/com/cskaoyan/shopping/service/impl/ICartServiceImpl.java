package com.cskaoyan.shopping.service.impl;/*
 *@Auther:强
 *@Date: 2022/4/22 23:10
 *@Version 1.0.0.0
 */

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
    public CartListByIdResponse getCartListById(HttpServletRequest request) {
        String access_token = CookieUtil.getCookieValue(request, "access_token");
        CartListByIdResponse cartListByIdResponse = new CartListByIdResponse();
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

        //userServiceImpl中的
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("username", freeJwt);

        List<Member> members = memberMapper.selectByExample(example);
        Member member = members.get(0);
        Long userId = member.getId();


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
        Item item = itemMapper.selectByPrimaryKey(request.getItemId());
        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setProductId(item.getId());
        cartProductDto.setProductImg(item.getImage());
        cartProductDto.setProductName(item.getTitle());
        cartProductDto.setSalePrice(item.getPrice());
        cartProductDto.setProductNum(request.getNum().longValue());
        //HashMap<Long, CartProductDto> longCartProductDtoHashMap = new HashMap<Long, CartProductDto>();
        //longCartProductDtoHashMap.put(request.getUserId(),cartProductDto);


        RMap<String, CartProductDto> map = redissonClient.getMap("SuiBian");

        // ArrayList<Object> objects = new ArrayList<>(map.values());
        map.put(String.valueOf(item.getId()), cartProductDto);
        map.put(request.getUserId().toString(), cartProductDto);
        AddCartResponse addCartResponse = new AddCartResponse();
        addCartResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        addCartResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return addCartResponse;
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
