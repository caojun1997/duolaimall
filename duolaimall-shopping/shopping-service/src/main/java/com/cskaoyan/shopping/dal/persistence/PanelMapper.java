package com.cskaoyan.shopping.dal.persistence;

import com.cskaoyan.shopping.dal.entitys.Panel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PanelMapper extends Mapper<Panel> {

    List<Panel> selectPanelContentById(@Param("panelId") Integer panelId);
}