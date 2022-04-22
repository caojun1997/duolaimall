package com.cskaoyan.shopping;

import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShoppingTest {

    @Autowired
    ItemMapper itemMapper;

    // 测试数据库
    @Test
    public void testItem() {
        Item item = itemMapper.selectByPrimaryKey(100046401);
    }

//    // 测试服务业务
//    @Autowired
//    IProductService productService;
    public void testService() {

    }

    // 测试服务的Controller
    public void testController() {
        RestTemplate restTemplate = new RestTemplate();
        // 发送http请求测试
    }



}
