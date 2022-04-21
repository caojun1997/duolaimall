package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.converter.ITestProductConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.ITestProductDetailDto;
import com.cskaoyan.shopping.dto.ITestProductRequest;
import com.cskaoyan.shopping.dto.ITestProductResponse;
import com.cskaoyan.shopping.service.ITestProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITestProductServiceImpl implements ITestProductService {


    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ITestProductConverter testProductConverter;

    @Override
    public ITestProductResponse getProductDetail(ITestProductRequest request) {

        ITestProductResponse iTestProductResponse = new ITestProductResponse();

        try {
            request.requestCheck();
            Item item = itemMapper.selectByPrimaryKey(request.getProductId());
            ITestProductDetailDto productDetailDto = testProductConverter.testProductDoToProductDto(item);

            // 业务执行成功
            iTestProductResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
            iTestProductResponse.setITestProductDetailDto(productDetailDto);
            iTestProductResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            iTestProductResponse.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            iTestProductResponse.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());
        }
        return iTestProductResponse;
    }
}
