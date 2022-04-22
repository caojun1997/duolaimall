package com.cskaoyan.order.form;

import lombok.Data;

@Data
public class PageInfo {

    private Integer page;

    private Integer size;

    private String sort;

    private Integer priceGt;

    private Integer priceLte;

    private Long cid;
}
