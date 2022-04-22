package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.dto.ITestProductDetailDto;
import com.cskaoyan.shopping.dto.ITestProductRequest;
import com.cskaoyan.shopping.dto.ITestProductResponse;
import com.cskaoyan.shopping.service.ITestProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProductController {

    @Autowired
    ITestProductService iTestProductService;

    @GetMapping("/shopping/test")
    public ResponseData getProductDetail(Long productId) {
        ITestProductRequest request = new ITestProductRequest();
        request.setProductId(productId);

        ITestProductResponse productDetail = iTestProductService.getProductDetail(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(productDetail.getCode())) {
            return new ResponseUtil().setData(productDetail.getITestProductDetailDto());
        }

        return new ResponseUtil().setErrorMsg(productDetail.getMsg());
    }
}
