package com.cskaoyan.shopping.converter;


import com.cskaoyan.shopping.dal.entitys.ItemCat;
import com.cskaoyan.shopping.dto.ProductCateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductCateConverter {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "name",target = "name"),
            @Mapping(source = "parentId",target = "parentId"),
            @Mapping(source = "isParent",target = "isParent"),
            @Mapping(source = "icon",target = "iconUrl")
    })
    ProductCateDto item2Dto(ItemCat itemCat);

    List<ProductCateDto> items2Dto(List<ItemCat> items);
}
