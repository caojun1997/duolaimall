package com.cskaoyan.shopping.dal.entitys;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class PanelContentItem {

    private Integer id;
    private Integer panelId;
    private Integer type;
    private Long productId;
    private Integer sortOrder;
    private String fullUrl;
    private String picUrl;
    private String picUrl2;
    private String picUrl3;
    private Date created;
    private Date updated;
    private String productName;
    private BigDecimal salePrice;
    private String subTitle;
}
