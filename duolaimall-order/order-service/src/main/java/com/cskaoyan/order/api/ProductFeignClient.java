package com.cskaoyan.order.api;

import com.cskaoyan.mall.api.ProductService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("duolai-shopping")
public interface ProductFeignClient extends ProductService {
}
