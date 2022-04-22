package com.cskaoyan.shopping.service.remote;

import com.cskaoyan.mall.api.ProductService;
import com.cskaoyan.mall.dto.AllItemResponse;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import com.cskaoyan.shopping.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    @Autowired
    IProductService iProductService;

    @PostMapping(value = "/rpc/detail")
    @Override
    public ProductDetailResponse getProductDetail(ProductDetailRequest request) {
        return iProductService.getProductDetail(request);
    }

    @GetMapping("/rpc/items")
    @Override
    public AllItemResponse getAllProductItem() {
        return null;
    }
}
