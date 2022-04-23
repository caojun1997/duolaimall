package com.cskaoyan.shopping.converter;

import com.cskaoyan.mall.dto.ItemDto;
import com.cskaoyan.mall.dto.ProductDetailDto;
import com.cskaoyan.mall.dto.ProductDto;
import com.cskaoyan.shopping.dal.entitys.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDetailConverter {

    @Mappings({
            @Mapping(source = "id", target = "productId"),
            @Mapping(source = "title", target = "productName"),
            @Mapping(source = "price", target = "salePrice"),
            @Mapping(source = "sellPoint", target = "subTitle"),
            @Mapping(source = "imageBig", target = "productImageBig"),
    })
    ProductDetailDto item2ProductDetailDto(Item item);

    List<ProductDetailDto> items2ProductDetailDtos(List<Item> items);

}
