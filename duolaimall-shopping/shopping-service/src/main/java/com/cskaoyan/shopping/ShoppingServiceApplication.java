package com.cskaoyan.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cskaoyan.shopping.dal")
//@ComponentScan({"com.cskaoyan.shopping.service", "com.cskaoyan.shopping.converter", "com.cskaoyan.shopping.controller"})
public class ShoppingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingServiceApplication.class, args);
    }
}
