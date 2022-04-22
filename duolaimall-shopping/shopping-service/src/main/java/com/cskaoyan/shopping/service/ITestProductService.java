package com.cskaoyan.shopping.service;

import com.cskaoyan.shopping.dto.ITestProductDetailDto;
import com.cskaoyan.shopping.dto.ITestProductRequest;
import com.cskaoyan.shopping.dto.ITestProductResponse;

public interface ITestProductService {

    ITestProductResponse getProductDetail(ITestProductRequest request);
}
