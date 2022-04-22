package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.constants.GlobalConstants;
import com.cskaoyan.shopping.converter.ContentConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.entitys.PanelContentItem;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dal.persistence.PanelMapper;
import com.cskaoyan.shopping.dto.HomePageResponse;
import com.cskaoyan.shopping.dto.PanelContentDto;
import com.cskaoyan.shopping.dto.PanelContentItemDto;
import com.cskaoyan.shopping.dto.PanelDto;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author cao jun
 * @description: TODO
 * @date 2022/4/22 17:16
 */
@Service
public class IHomeServiceImp implements IHomeService {
    @Autowired
    PanelMapper panelMapper;
    @Autowired
    PanelContentMapper panelContentMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ContentConverter contentConverter;


    /**
     * homepage
     * cao jun
     * 2022年4月22日17:19:55
     */
    @Override
    public HomePageResponse homepage() {
        HashSet<PanelDto> panelDtos = new HashSet<>();
        List<Panel> panels = panelMapper.selectAll();
        for (Panel panel : panels) {
            Sqls.Criteria criteria = new Sqls.Criteria();
            Example example = new Example(PanelContent.class);
            example.createCriteria().andEqualTo("panelId", panel.getId());
            List<PanelContent> panelContents = panelContentMapper.selectByExample(example);
            List<PanelContentItem> panelContentItemsList = new ArrayList<>();
            for (PanelContent panelContent : panelContents) {
                PanelContentItem panelContentItem = contentConverter.panelContent2DPanelContentItem(panelContent);
                Item item = itemMapper.selectByPrimaryKey(panelContent.getProductId());
                panelContentItem = contentConverter.Item2DPanelContentItem(item);
                panelContentItemsList.add(panelContentItem);
            }
            List<PanelContentItemDto> panelContentItemDtos = contentConverter.panelContentItem2Dto(panelContentItemsList);
            PanelDto panelDto = contentConverter.panel2Dto(panel);
            panelDto.setPanelContentItems(panelContentItemDtos);
            panelDtos.add(panelDto);
        }
        HomePageResponse homePageResponse = new HomePageResponse();
        homePageResponse.setPanelContentItemDtos(panelDtos);
        homePageResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
        homePageResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return homePageResponse;
    }
}
