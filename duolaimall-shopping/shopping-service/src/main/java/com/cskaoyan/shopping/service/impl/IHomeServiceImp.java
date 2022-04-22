package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dal.persistence.PanelMapper;
import com.cskaoyan.shopping.dto.HomePageResponse;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * @author cao jun
 * @description: TODO
 * @date 2022/4/22 17:16
 */
@Service
public class IHomeServiceImp implements IHomeService {
    @Autowired
    PanelMapper panelMapper;
    @Autowired
    PanelContentMapper panelContentMapper;


    /**
     *  homepage
     *  cao jun
     *  2022年4月22日17:19:55
     */
    @Override
    public HomePageResponse homepage() {
        List<Panel> panels = panelMapper.selectAll();
        for (Panel panel : panels) {
            Sqls.Criteria criteria = new Sqls.Criteria();
            Example example = new Example(PanelContent.class);
            example.createCriteria().andEqualTo(String.valueOf(panel.getId()), "pane_id");
            List<PanelContent> panelContents = panelContentMapper.selectByExample(example);

        }
        return null;
    }
}
