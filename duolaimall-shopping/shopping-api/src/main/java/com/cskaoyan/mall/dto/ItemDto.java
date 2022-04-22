package com.cskaoyan.mall.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;

/*
     Item对应的属性完全相同的Dto对象
 */
@Data
public class ItemDto {
    private Long id;

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String image;

    private Long cid;

    private Integer status;

    private Date created;

    private Date updated;

    @Getter(AccessLevel.NONE)
    private String imageBig;

    public String getImageBig(){
        if (image != null && !"".equals(image)) {
            String[] strings = image.split(",");
            return strings[0];
        }
        return null;
    }
}
