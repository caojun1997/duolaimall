package com.cskaoyan.shopping;

import com.cskaoyan.shopping.converter.ProductConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.CartListByIdResponse;
import com.cskaoyan.shopping.dto.CartProductDto;
import com.cskaoyan.shopping.dto.CartProductTimeDto;
import com.cskaoyan.shopping.dto.CartProductTimeDto;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    ProductConverter productConverter;

    // 测试redis
    @Test
    public void testRedis() throws ParseException {

        RMap<String, CartProductTimeDto> map = redissonClient.getMap("4");//利用key:userId

        // 加入购物车
        BigDecimal bigDecimal = new BigDecimal(8999);

        //字符串转Date类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        CartProductDto cartProductDto1 = new CartProductDto(1l, bigDecimal, 90l, 7l, "true", "布衣", "123456789");
        CartProductTimeDto cartProductTimeDto1 = productConverter.cartProductDto2Time(cartProductDto1);
        cartProductTimeDto1.setCartAddTime(format.parse("2020-02-10 02:02:02"));
        CartProductDto cartProductDto2 = new CartProductDto(2l, bigDecimal, 90l, 7l, "true", "布衣", "123456789");
        CartProductTimeDto cartProductTimeDto2 = productConverter.cartProductDto2Time(cartProductDto2);
        cartProductTimeDto2.setCartAddTime(format.parse("2020-02-01 02:02:02"));
        CartProductDto cartProductDto3 = new CartProductDto(3l, bigDecimal, 90l, 7l, "true", "布衣", "123456789");
        CartProductTimeDto cartProductTimeDto3 = productConverter.cartProductDto2Time(cartProductDto3);
        cartProductTimeDto3.setCartAddTime(format.parse("2020-02-08 02:02:02"));
        CartProductDto cartProductDto4 = new CartProductDto(4l, bigDecimal, 90l, 7l, "true", "布衣", "123456789");
        CartProductTimeDto cartProductTimeDto4 = productConverter.cartProductDto2Time(cartProductDto4);
        cartProductTimeDto4.setCartAddTime(format.parse("2020-02-03 02:02:02"));
        CartProductDto cartProductDto5 = new CartProductDto(5l, bigDecimal, 90l, 7l, "true", "布衣", "123456789");
        CartProductTimeDto cartProductTimeDto5 = productConverter.cartProductDto2Time(cartProductDto5);
        cartProductTimeDto5.setCartAddTime(format.parse("2020-02-09 02:02:02"));
        CartProductDto cartProductDto6 = new CartProductDto(6l, bigDecimal, 90l, 7l, "true", "布衣", "123456789");
        CartProductTimeDto cartProductTimeDto6 = productConverter.cartProductDto2Time(cartProductDto6);
        cartProductTimeDto6.setCartAddTime(format.parse("2020-02-25 02:02:02"));

        map.fastPut("1", cartProductTimeDto1);
        map.fastPut("3", cartProductTimeDto3);
        map.fastPut("4", cartProductTimeDto4);
        map.fastPut("5", cartProductTimeDto5);
        map.fastPut("6", cartProductTimeDto6);


        //首先从Map中获取Map中所有的Value(CartProductDto)组成的List
        Collection<CartProductTimeDto> cartProductTimeDtos = map.values();
        List<CartProductTimeDto> timeDtos = new ArrayList<CartProductTimeDto>(cartProductTimeDtos);
        //System.out.println(cartProductTimeDtos);


        //自定义排序:
        Collections.sort(timeDtos, new Comparator<CartProductTimeDto>() {
            @Override
            public int compare(CartProductTimeDto o1, CartProductTimeDto o2) {
                return (int) (o2.getCartAddTime().getTime() - o1.getCartAddTime().getTime());
            }
        });

        //System.out.println(cartProductTimeDtos);

    }

    }

