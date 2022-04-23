package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.*;
import com.cskaoyan.shopping.converter.*;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.entitys.PanelContentItem;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dal.persistence.PanelMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.IProductService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class IProductServiceImpl implements IProductService {


    @Autowired
    ItemMapper itemMapper;
    @Autowired
    PanelMapper panelMapper;
    @Autowired
    PanelContentMapper panelContentMapper;

    @Autowired
    ProductDetailConverter productDetailConverter;
    @Autowired
    ContentConverter contentConverter;
    @Autowired
    ProductConverter productConverter;


    /**
     * @author zwy
     * @date 2022/4/22 21:11
     */
    @Override
    public ProductDetailResponse getProductDetail(ProductDetailRequest request) {

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        try {
            request.requestCheck();
            Item item = itemMapper.selectByPrimaryKey(request.getId());
            ProductDetailDto productDetailDto = productDetailConverter.item2ProductDetailDto(item);
            String image = item.getImage();
            if (image != null && !"".equals(image)) {
                List<String> smallImages = Arrays.asList(image.split(","));
                productDetailDto.setProductImageSmall(smallImages);
            }

            productDetailResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
            productDetailResponse.setProductDetailDto(productDetailDto);
            productDetailResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            productDetailResponse.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            productDetailResponse.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());

        }

        return productDetailResponse;
    }

    @Override
    public AllProductResponse getAllProduct(AllProductRequest request) {
        AllProductResponse allProductResponse = new AllProductResponse();
        Integer page = request.getPage();//页码
        Integer size = request.getSize();//每页条数
        String sort = request.getSort();//是否排序，不一定有

        Integer priceGt = request.getPriceGt();//价格最小值，不一定有
        Integer priceLte = request.getPriceLte();//价格最大值，不一定有

        Long cid = request.getCid();//类目
        if (page != null && size != null) {
            PageHelper.startPage(page, size);//分页插件
        }


        try {
            Example example = new Example(Item.class);
            Example.Criteria criteria = example.createCriteria();
            //添加查询条件
            if (cid != null && cid.longValue() != 0) {
                criteria.andEqualTo("cid", cid);
            }
            if (sort != null && sort.length() != 0) {
                example.setOrderByClause(sort + " id");//默认order是id字段
            }
            //如果传入最低价格和最高价格
            if (priceGt != null) {
                criteria.andGreaterThanOrEqualTo("price", priceGt);
            }
            if (priceLte != null) {
                criteria.andLessThanOrEqualTo("price", priceLte);
            }

            List<Item> items = itemMapper.selectByExample(example);
            List<ProductDto> productDtos = productConverter.items2Dto(items);

            PageInfo<ProductDto> pageInfo = new PageInfo<>(productDtos);
            long total = pageInfo.getTotal();

            allProductResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
            allProductResponse.setProductDtoList(productDtos);
            allProductResponse.setTotal(total);
            allProductResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            allProductResponse.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            allProductResponse.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());
        }

        return allProductResponse;
    }


    @Override
    public RecommendResponse getRecommendGoods() {

        RecommendResponse recommendResponse = new RecommendResponse();
        try {
            Example example = new Example(Panel.class);
            example.createCriteria().andEqualTo("name", "热门推荐");
            List<Panel> panels = panelMapper.selectByExample(example);
            //转成List<PanelDto>
            List<PanelDto> panelDtos = contentConverter.panels2Dto(panels);
            //备用
            List<PanelContentItemDto> panelContentItemDtos = new ArrayList<>();

            for (Panel panel1 : panels) {

                Example example1 = new Example(PanelContent.class);
                example1.createCriteria().andEqualTo("panelId", 6);
                List<PanelContent> panelContents = panelContentMapper.selectByExample(example1);
                //遍历其中每个元素，并添加上item信息，封装进PanelContentItemDto
                for (PanelContent panelContent : panelContents) {
                    Example example2 = new Example(Item.class);
                    if (panelContent.getProductId() == null) {
                        continue;
                    }
                    Item item = itemMapper.selectByPrimaryKey(panelContent.getProductId());
                    PanelContentItem panelContentItem = contentConverter.panelContent2DPanelContentItem(panelContent);
                    if (panelContentItem == null) {
                        continue;
                    }
                    panelContentItem.setSubTitle(item.getSellPoint());
                    panelContentItem.setProductName(item.getTitle());
                    panelContentItem.setSalePrice(item.getPrice());

                    PanelContentItemDto panelContentItemDto = contentConverter.panelContentItem2PanelContentItemDto(panelContentItem);
                    panelContentItemDtos.add(panelContentItemDto);
                }

                PanelDto panelDto = contentConverter.panel2Dto(panel1);
                panelDto.setPanelContentItems(panelContentItemDtos);
                panelDtos.add(panelDto);
            }
            recommendResponse.setCode(ShoppingRetCode.SUCCESS.getCode());
            recommendResponse.setPanelFinalDtos(panelDtos);
            recommendResponse.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            recommendResponse.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            recommendResponse.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());

        }
        return recommendResponse;
    }


    @Override
    public AllItemResponse getAllItems() {
        return null;
    }
}
