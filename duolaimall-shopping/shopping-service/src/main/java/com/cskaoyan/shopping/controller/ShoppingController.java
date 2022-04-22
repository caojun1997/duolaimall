package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.dto.HomePageResponse;
import com.cskaoyan.shopping.dto.NavListResponse;
import com.cskaoyan.shopping.service.IContentService;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    IHomeService iHomeService;
    @Autowired
    IContentService iContentService;
    @Autowired

    /**
     * cao jun
     * 2022年4月22日16:57:20
     */
    @GetMapping("homepage")
    public ResponseData homepage() {

        HomePageResponse homePageResponse = iHomeService.homepage();
        if (ShoppingRetCode.SUCCESS.getCode().equals(homePageResponse.getCode())) {
            return new ResponseUtil().setData(homePageResponse.getPanelContentItemDtos());
        }
        return new ResponseUtil().setErrorMsg(homePageResponse.getMsg());
    }

    /**
     * cao jun
     * 2022年4月22日23:32:37
     */
    @GetMapping("navigation")
    public ResponseData navigation() {

        NavListResponse navResponse = iContentService.queryNavList();
        if (ShoppingRetCode.SUCCESS.getCode().equals(navResponse.getCode())) {
            return new ResponseUtil().setData(navResponse.getPannelContentDtos());
        }
        return new ResponseUtil().setErrorMsg(navResponse.getMsg());
    }

    @GetMapping("categories")
    public ResponseData categories() {



        return null;
    }
}
