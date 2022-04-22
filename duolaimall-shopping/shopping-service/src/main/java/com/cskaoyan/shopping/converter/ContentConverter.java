package com.cskaoyan.shopping.converter;


import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.entitys.PanelContentItem;
import com.cskaoyan.shopping.dto.PanelContentDto;
import com.cskaoyan.shopping.dto.PanelContentItemDto;
import com.cskaoyan.shopping.dto.PanelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentConverter {

    @Mappings({})
    PanelContentDto panelContent2Dto(PanelContent panelContent);

    List<PanelContentDto> panelContentList2Dto(List<PanelContent> panelContent);

    @Mappings({})
    PanelContentDto panelContentItem2Dto(PanelContentItem panelContentItem);

    PanelContentItemDto panelContentItem2PanelContentItemDto(PanelContentItem panelContentItem);

    List<PanelContentItemDto> panelContentItem2Dto(List<PanelContentItem> panelContentItems);



    @Mappings({})
    PanelContentItem panelContent2DPanelContentItem(PanelContent panelContent);


    @Mappings({})
    PanelContentItem Item2DPanelContentItem(Item Item);

    @Mappings({})
    PanelDto panel2Dto(Panel panel);

    List<PanelContentDto> panelContents2Dto(List<PanelContent> panelContents);


    // panelContentDto转换成PanelContentItemDto
    PanelContentItemDto panelContent2ContentDto(PanelContentDto panelContent);

}
