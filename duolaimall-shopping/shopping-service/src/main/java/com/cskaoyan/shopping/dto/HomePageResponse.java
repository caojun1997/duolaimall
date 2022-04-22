package com.cskaoyan.shopping.dto;


import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class HomePageResponse extends AbstractResponse {

    private List<PanelDto> panelContentItemDtos;
}
