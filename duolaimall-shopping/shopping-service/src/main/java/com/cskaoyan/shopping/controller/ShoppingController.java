package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import com.cskaoyan.shopping.service.IContentService;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    IHomeService iHomeService;
    @Autowired
    IContentService iContentService;

    @Autowired

    /**
     * cao jun
     * 2022年4月22日16:57:20
     * 首页显示分类商品
     */
    @GetMapping("homepage")
    public ResponseData homepage() {

        HomePageResponse homePageResponse = iHomeService.homepage();
        if (ShoppingRetCode.SUCCESS.getCode().equals(homePageResponse.getCode())) {
            return new ResponseUtil().setData(homePageResponse.getPanelContentItemDtos());
        }
        return new ResponseUtil().setErrorMsg(homePageResponse.getMsg());
    }

    /**
     * cao jun
     * 2022年4月22日23:32:37
     * 首页的导航栏
     */
    @GetMapping("navigation")
    public ResponseData navigation() {

        NavListResponse navResponse = iContentService.queryNavList();
        if (ShoppingRetCode.SUCCESS.getCode().equals(navResponse.getCode())) {
            return new ResponseUtil().setData(navResponse.getPannelContentDtos());
        }
        return new ResponseUtil().setErrorMsg(navResponse.getMsg());
    }

    /**
     * cao jun
     * 2022年4月23日09:03:03
     */
    @GetMapping("categories")
    public ResponseData categories() {

        return null;
    }


    @Autowired
    ICartService iCartService;

    /**
     * cao jun
     * 2022年4月23日09:03:25
     * 购物车的获取商品列表
     */
    @GetMapping("cart")
    public ResponseData cart(@RequestBody Map param) {

        Integer userId = (Integer) param.get("userId");
        Integer productNum = (Integer) param.get("productNum");
        Integer productId = (Integer) param.get("productId");
        Integer checked = (Integer) param.get("checked");
        if (userId != null && productId != null && productNum != null && checked != null) {
            // 更新购物车商品
            UpdateCartNumRequest updateCartNumRequest = new UpdateCartNumRequest();
            iCartService.updateCartNum(updateCartNumRequest);

        }
        if (userId != null && productId != null && productNum != null && checked == null) {
            // 加入购物车
            AddCartRequest addCartRequest = new AddCartRequest();
            addCartRequest.setUserId(Long.valueOf(userId));
            addCartRequest.setItemId(Long.valueOf(productId));
            addCartRequest.setNum(productNum);
            AddCartResponse addCartResponse = iCartService.addToCart(addCartRequest);


        }
        if (userId != null && productId == null && productNum == null && checked == null) {
            // 获得购物车商品列表
            CartListByIdRequest cartListByIdRequest = new CartListByIdRequest();
            cartListByIdRequest.setUserId(Long.valueOf(userId));
            CartListByIdResponse cartListByIdResponse = iCartService.getCartListById(cartListByIdRequest);
            if (ShoppingRetCode.SUCCESS.getCode().equals(cartListByIdResponse.getCode())) {
                return new ResponseUtil().setData(cartListByIdResponse.getCartProductDtos());
            } else {
                return new ResponseUtil().setErrorMsg(Integer.valueOf(cartListByIdResponse.getCode()), cartListByIdResponse.getMsg());
            }
        }

         // 上面流程错误说明都没成功返回一个系统错误
        return new ResponseUtil().setErrorMsg(Integer.valueOf(ShoppingRetCode.SYSTEM_ERROR.getCode()), ShoppingRetCode.SYSTEM_ERROR.getMessage());
    }
}
