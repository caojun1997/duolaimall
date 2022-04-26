package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import com.cskaoyan.shopping.service.IContentService;
import com.cskaoyan.shopping.dto.AddCartRequest;
import com.cskaoyan.shopping.dto.AddCartResponse;
import com.cskaoyan.shopping.dto.HomePageResponse;
import com.cskaoyan.shopping.service.ICartService;
import com.cskaoyan.shopping.service.IHomeService;
import com.cskaoyan.shopping.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    IHomeService service;

    @Autowired
    ICartService iCartService;

    @Autowired
    IHomeService iHomeService;
    @Autowired
    IContentService iContentService;
    @Autowired
    IProductService iProductService;


    /**
     * @author zwy
     * @date 2022/4/22 19:41
     */
    @GetMapping("/shopping/product/{id}")
    public ResponseData getProductDetail(@PathVariable("id") Long productId) {//@PathVariable注解 接收请求参数中占位符的值
        ProductDetailRequest request = new ProductDetailRequest();
        request.setId(productId);
        ProductDetailResponse productDetail = iProductService.getProductDetail(request);

        if (ShoppingRetCode.SUCCESS.getCode().equals(productDetail.getCode())) {
            return new ResponseUtil().setData(productDetail.getProductDetailDto());
        }
        return new ResponseUtil().setErrorMsg(productDetail.getMsg());
    }


    /**
     * @author zwy
     * @date 2022/4/22 22:30
     */
    @GetMapping("/shopping/recommend")
    public ResponseData getRecommendGoods() {

        RecommendResponse recommendResponse = iProductService.getRecommendGoods();
        if (ShoppingRetCode.SUCCESS.getCode().equals(recommendResponse.getCode())) {
            return new ResponseUtil().setData(recommendResponse.getPanelFinalDtos());
        }
        return new ResponseUtil().setErrorMsg(recommendResponse.getMsg());
    }


    /**
     * @author zwy
     * @date 2022/4/23 10:34
     */
    @GetMapping("/shopping/goods")
    public ResponseData getAllProduct(AllProductRequest request) {

        AllProductResponse allProductResponse = iProductService.getAllProduct(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(allProductResponse.getCode())) {
            return new ResponseUtil().setData(allProductResponse);

        }
        return new ResponseUtil().setErrorMsg(allProductResponse.getMsg());
    }


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

    @PostMapping("carts")
    public ResponseData addToCarts(@RequestBody AddCartRequest request){
        AddCartResponse addCartResponse = iCartService.addToCart(request);
        if(ShoppingRetCode.SUCCESS.getCode().equals(addCartResponse.getCode())){
            return new ResponseUtil().setData(addCartResponse.getAddCartResultItemDtos());
        }
        return new ResponseUtil().setErrorMsg(addCartResponse.getMsg());

    }

    @PutMapping("carts")
    public ResponseData updateCarts(@RequestBody UpdateCartNumRequest request){
        UpdateCartNumResponse updateCartNumResponse = iCartService.updateCartNum(request);
        if(ShoppingRetCode.SUCCESS.getCode().equals(updateCartNumResponse.getCode())){
            return new ResponseUtil().setData(updateCartNumResponse.getCartProducTimetDto());
        }
        return new ResponseUtil().setErrorMsg(updateCartNumResponse.getMsg());

    }

    @DeleteMapping("carts")
    public ResponseData deleteCart(DeleteCartItemRequest request){
        DeleteCartItemResponse deleteCartItemResponse = iCartService.deleteCartItem(request);
        if(ShoppingRetCode.SUCCESS.getCode().equals(deleteCartItemResponse.getCode())){
            return new ResponseUtil().setData(deleteCartItemResponse.getStatus());
        }
        return new ResponseUtil().setErrorMsg(deleteCartItemResponse.getMsg());
    }


    @DeleteMapping("items")
    public ResponseData deleteCheckedCartItems(DeleteCheckedItemRequest request){
        DeleteCheckedItemResposne deleteCheckedItemResposne = iCartService.deleteCheckedItem(request);
        CartListByIdRequest cartListByIdRequest = new CartListByIdRequest();
        cartListByIdRequest.setUserId(request.getUserId());
        iCartService.getCartListById(cartListByIdRequest);
        if(ShoppingRetCode.SUCCESS.getCode().equals(deleteCheckedItemResposne.getCode())){
            return new ResponseUtil().setData(deleteCheckedItemResposne.getStatus());
        }
        return new ResponseUtil().setErrorMsg(deleteCheckedItemResposne.getMsg());
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
