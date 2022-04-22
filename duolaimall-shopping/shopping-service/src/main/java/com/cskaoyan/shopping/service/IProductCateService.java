package com.cskaoyan.shopping.service;


import com.cskaoyan.shopping.dto.AllProductCateRequest;
import com.cskaoyan.shopping.dto.AllProductCateResponse;

public interface IProductCateService {
    /**
     * 获取所有产品分类
     * @param request
     * @return
     */
    AllProductCateResponse getAllProductCate(AllProductCateRequest request);
}
