package com.cskaoyan.shopping.converter;

import com.cskaoyan.shopping.dal.entitys.ItemCat;
import com.cskaoyan.shopping.dto.ProductCateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 商品类别dto的转换
 * cao jun
 * 2022年4月23日20:48:35
 */
@Mapper(componentModel = "spring")
public interface ItemCatConverter {
    @Mappings({@Mapping(target ="iconUrl",source = "icon")})
    ProductCateDto itemCat2ProductCateDto(ItemCat itemCat);

    List<ProductCateDto> itemCat2ProductCateDtoList(List<ItemCat> itemCats);

}
