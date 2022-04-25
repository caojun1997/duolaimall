package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.converter.ContentConverter;
import com.cskaoyan.shopping.dal.entitys.ItemCat;
import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.persistence.ItemCatMapper;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dal.persistence.PanelMapper;
import com.cskaoyan.shopping.dto.NavListResponse;
import com.cskaoyan.shopping.dto.PanelContentDto;
import com.cskaoyan.shopping.service.IContentService;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    ItemCatMapper itemCatMapper;

    @Override
    public NavListResponse queryNavList() {
        List<PanelContent> panelContents = panelContentMapper.selectAll();
        NavListResponse navListResponse = new NavListResponse();
        ArrayList<PanelContentDto> panelContentDtos = new ArrayList<>();
        PanelContentDto panelContentDto1 = new PanelContentDto(55,0,1,null,1,null,"手机",null,null);
        PanelContentDto panelContentDto2 = new PanelContentDto(58,0,1,null,2,null,"官方配件",null,null);
        PanelContentDto panelContentDto3 = new PanelContentDto(59,0,1,null,3,null,"服饰箱包",null,null);
        PanelContentDto panelContentDto4 = new PanelContentDto(60,0,1,null,4,null,"畅呼吸",null,null);
        PanelContentDto panelContentDto5 = new PanelContentDto(61,0,1,null,5,null,"服务",null,null);
        panelContentDtos.add(panelContentDto1);
        panelContentDtos.add(panelContentDto2);
        panelContentDtos.add(panelContentDto3);
        panelContentDtos.add(panelContentDto4);
        panelContentDtos.add(panelContentDto5);
//        List<PanelContentDto> panelContentDtos = contentConverter.panelContentList2Dto(panelContents);

//        for (PanelContentDto panelContentDto : panelContentDtos) {
//            ItemCat itemCat = itemCatMapper.selectByPrimaryKey(panelContentDto.getPanelId());
//            if (itemCat==null){
//                panelContentDtos.remove(panelContentDto);
//                continue;
//            }
//            panelContentDto.setPicUrl(itemCat.getName());
//        }
        navListResponse.setPannelContentDtos(panelContentDtos);
        navListResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        navListResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return navListResponse;
    }
}
