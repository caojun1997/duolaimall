package com.cskaoyan.mall.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
public class AllItemResponse extends AbstractResponse {
    List<ItemDto> allItems;
}
