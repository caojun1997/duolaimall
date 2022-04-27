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
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        String userId = request.getUserId().toString();
        Long num = request.getNum().longValue();
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.7.4:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RMap<Long, CartProductDto> map = redissonClient.getMap(userId);
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
