package com.cskaoyan.order.dal.persistence;

import com.cskaoyan.order.dal.entitys.Stock;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StockMapper extends Mapper<Stock> {
 void updateStock(Stock stock);
 Stock selectStockForUpdate(Long itemId);
 Stock selectStock(Long itemId);

 List<Stock> findStocksForUpdate(@Param("itemIds") List<Long> itemIds);
}