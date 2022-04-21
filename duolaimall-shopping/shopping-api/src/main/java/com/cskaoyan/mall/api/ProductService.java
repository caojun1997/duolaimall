package com.cskaoyan.mall.api;

import com.cskaoyan.mall.dto.AllItemResponse;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import org.springframework.web.bind.annotation.*;

public interface ProductService {

    @PostMapping(value = "/rpc/detail")
    ProductDetailResponse getProductDetail(@RequestBody ProductDetailRequest request);

    @GetMapping("/rpc/items")
    AllItemResponse getAllProductItem();
}
