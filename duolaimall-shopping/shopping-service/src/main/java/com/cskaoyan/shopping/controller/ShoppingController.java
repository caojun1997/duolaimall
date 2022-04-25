package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.*;
import com.cskaoyan.shopping.dto.AddCartRequest;
import com.cskaoyan.shopping.dto.AddCartResponse;
import com.cskaoyan.shopping.dto.HomePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    IHomeService iHomeService;

    @Autowired
    ICartService icartService;

    @Autowired
    IContentService iContentService;

    @Autowired
    IProductService iProductService;

    @Autowired
    IProductCateService iProductCateService;





    /**
     * @author zwy
     * @date 2022/4/23 17:45
     */
    @GetMapping("carts")
    public ResponseData addToCarts(HttpServletRequest request) {
        CartListByIdResponse cartListByIdResponse = icartService.getCartListById(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(cartListByIdResponse.getCode())) {
            return new ResponseUtil().setData(cartListByIdResponse.getCartProductDtos());
        }
        return new ResponseUtil().setErrorMsg(cartListByIdResponse.getMsg());

    }


    /**
     * @author zwy
     * @date 2022/4/22 19:41
     */
    @GetMapping("/product/{id}")
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
    @GetMapping("/recommend")
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
    @GetMapping("/goods")
    public ResponseData getAllProduct(AllProductRequest request) {

        AllProductResponse allProductResponse = iProductService.getAllProduct(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(allProductResponse.getCode())) {
            AllProductResponse2 allProductResponse2 = new AllProductResponse2();
            allProductResponse2.setProductDtoList(allProductResponse.getProductDtoList());
            allProductResponse2.setTotal(allProductResponse.getTotal());
            return new ResponseUtil().setData(allProductResponse2);

        }
        return new ResponseUtil().setErrorMsg(allProductResponse.getMsg());
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
     * 商品分类信息
     */
    @GetMapping("categories")
    public ResponseData categories(AllProductCateRequest allProductCateRequest) {
        AllProductCateResponse allProductCateResponse=iProductCateService.getAllProductCate(allProductCateRequest);
        if (ShoppingRetCode.SUCCESS.getCode().equals(allProductCateResponse.getCode())){
            return new ResponseUtil().setData(allProductCateResponse.getProductCateDtoList());
        }

        return new ResponseUtil().setErrorMsg(allProductCateResponse.getMsg());
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


}
