package com.cskaoyan.shopping.converter;

import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dto.ITestProductDetailDto;
import com.cskaoyan.shopping.dto.PanelContentItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.xml.transform.Source;

@Mapper(componentModel = "spring")
public interface ITestProductConverter {

    @Mappings({
            @Mapping(source = "title", target = "productName"),
            @Mapping(source = "image", target = "imgUrls")
    })
    ITestProductDetailDto testProductDoToProductDto(Item item);

    // panelContent转换成Dto

    PanelContentItemDto panelContent2ContentDto(PanelContent panelContent);


}
