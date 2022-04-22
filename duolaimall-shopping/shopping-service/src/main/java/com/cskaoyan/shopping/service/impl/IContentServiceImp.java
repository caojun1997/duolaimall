package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.converter.ContentConverter;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dto.NavListResponse;
import com.cskaoyan.shopping.dto.PanelContentDto;
import com.cskaoyan.shopping.service.IContentService;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cao jun
 * @description: TODO
 * @date 2022/4/22 23:43
 */
@Service
public class IContentServiceImp implements IContentService {
    @Autowired
    PanelContentMapper panelContentMapper;
    @Autowired
    ContentConverter contentConverter;

    @Override
    public NavListResponse queryNavList() {
        List<PanelContent> panelContents = panelContentMapper.selectAll();
        NavListResponse navListResponse = new NavListResponse();
        List<PanelContentDto> panelContentDtos = contentConverter.panelContentList2Dto(panelContents);
        navListResponse.setPannelContentDtos(panelContentDtos);
        navListResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        navListResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return navListResponse;
    }
}
