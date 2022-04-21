package com.cskaoyan.shopping.service;

import com.cskaoyan.mall.dto.AllItemResponse;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import com.cskaoyan.shopping.dto.*;

public interface IProductService {

    /**
     * 查看商品明细
     * @param request
     * @return
     */
    ProductDetailResponse getProductDetail(ProductDetailRequest request);

    /**
     * 查询所有商品（分页）
     * @param request
     * @return
     */
    AllProductResponse getAllProduct(AllProductRequest request);

    /**
     * 获取推荐的商品板块
     * @return
     */
    RecommendResponse getRecommendGoods();

    AllItemResponse getAllItems();

}
