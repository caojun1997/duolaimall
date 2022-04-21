package com.cskaoyan.shopping.dal.persistence;


import com.cskaoyan.shopping.dal.entitys.Item;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ItemMapper extends Mapper<Item> {

    List<Item> selectItemFront(@Param("cid") Long cid,
                               @Param("orderCol") String orderCol, @Param("orderDir") String orderDir,
                               @Param("priceGt") Integer priceGt, @Param("priceLte") Integer priceLte);
}