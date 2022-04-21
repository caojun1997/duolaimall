package com.cskaoyan.shopping.converter;

import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dto.ITestProductDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ITestProductConverter {

    @Mappings({
            @Mapping(source = "title", target = "productName"),
            @Mapping(source = "image", target = "imgUrls")
    })
    ITestProductDetailDto testProductDoToProductDto(Item item);
}
