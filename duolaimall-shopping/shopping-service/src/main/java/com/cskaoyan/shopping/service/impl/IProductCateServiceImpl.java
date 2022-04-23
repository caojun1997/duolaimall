package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;

import com.cskaoyan.shopping.converter.ItemCatConverter;
import com.cskaoyan.shopping.dal.entitys.ItemCat;
import com.cskaoyan.shopping.dal.persistence.ItemCatMapper;
import com.cskaoyan.shopping.dto.AllProductCateRequest;
import com.cskaoyan.shopping.dto.AllProductCateResponse;
import com.cskaoyan.shopping.dto.CartProductDto;
import com.cskaoyan.shopping.dto.ProductCateDto;
import com.cskaoyan.shopping.service.IProductCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cao jun
 * @description: TODO
 * @date 2022/4/23 17:54
 */
@Service
public class IProductCateServiceImpl implements IProductCateService {
    @Autowired
    ItemCatMapper itemCatMapper;

    @Autowired
    ItemCatConverter itemCatConverter;

    @Override
    public AllProductCateResponse getAllProductCate(AllProductCateRequest request) {
        try {
            request.requestCheck();
            String sort = request.getSort();
            if (sort == null) {
                // sort为null的时候,默认降序排列
                Example example = new Example(ItemCat.class);
                example.setOrderByClause("sort_order"+" "+"desc");
                List<ItemCat> itemCats = itemCatMapper.selectByExample(example);
                AllProductCateResponse allProductCateResponse = new AllProductCateResponse();

                List<ProductCateDto> productCateDtos = itemCatConverter.itemCat2ProductCateDtoList(itemCats);
                allProductCateResponse.setProductCateDtoList(productCateDtos);
                allProductCateResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
                allProductCateResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
                return allProductCateResponse;
                // 返回所有的
            }else {
                // 不为null再进行排序
            }
        }catch (Exception e){
            AllProductCateResponse allProductCateResponse = new AllProductCateResponse();
            allProductCateResponse.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            allProductCateResponse.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());
            return allProductCateResponse;
        }
        AllProductCateResponse allProductCateResponse = new AllProductCateResponse();
        allProductCateResponse.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
        allProductCateResponse.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());
        return allProductCateResponse;
    }
}
