package com.cskaoyan.order.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class OrderCountResponse extends AbstractResponse {

    private int count;
}
