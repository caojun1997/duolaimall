package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.Set;

@Data
public class RecommendResponse extends AbstractResponse {

    private Set<PanelDto> panelContentItemDtos;

}
