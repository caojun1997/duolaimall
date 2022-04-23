package com.cskaoyan.shopping;

import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.CartProductDto;
import com.cskaoyan.shopping.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

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

    @Autowired
    RedissonClient redissonClient;
    // 测试redis
    @Test
    public void testRedis() {

        RMap<String, CartProductDto> map = redissonClient.getMap("3");
        // 加入购物车
//        BigDecimal bigDecimal = new BigDecimal(8999);
//        CartProductDto cartProductDto1 = new CartProductDto(1l,bigDecimal,90l,7l,"true","布衣","123456789");
//        CartProductDto cartProductDto2 = new CartProductDto(2l,bigDecimal,90l,7l,"true","布衣","123456789");
//        CartProductDto cartProductDto3 = new CartProductDto(3l,bigDecimal,90l,7l,"true","布衣","123456789");
//        CartProductDto cartProductDto4 = new CartProductDto(4l,bigDecimal,90l,7l,"true","布衣","123456789");
//        CartProductDto cartProductDto5 = new CartProductDto(5l,bigDecimal,90l,7l,"true","布衣","123456789");
//        CartProductDto cartProductDto6 = new CartProductDto(6l,bigDecimal,90l,7l,"true","布衣","123456789");
//
//
//        map.fastPut("1",cartProductDto1);
//        map.fastPut("3",cartProductDto3);
//        map.fastPut("4",cartProductDto4);
//        map.fastPut("5",cartProductDto5);
//        map.fastPut("6",cartProductDto6);

        ArrayList<CartProductDto> cartProductDtos = new ArrayList<>();
       //m

        for (String s : map.keySet()) {
            CartProductDto cartProductDto = map.get(s);
            cartProductDtos.add(cartProductDto);
        }
        Collection<CartProductDto> cartProductDtos1 = map.readAllValues();
        System.out.println(cartProductDtos);



    }


}
