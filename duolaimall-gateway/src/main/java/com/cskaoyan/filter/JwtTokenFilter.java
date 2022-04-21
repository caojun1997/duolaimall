package com.cskaoyan.filter;

import com.cskaoyan.mall.commons.constant.SysRetCodeConstants;
import com.cskaoyan.mall.commons.exception.GateWayException;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.properties.SkipUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@EnableConfigurationProperties(value = SkipUrlProperties.class)
public class JwtTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    SkipUrlProperties skillUrlProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取实际请求的path路径
        String currentUrl = exchange.getRequest().getURI().getPath();
        // 判断是否应该拦截该请求，验证其Token
        if (shouldSkip(currentUrl, exchange.getRequest().getMethod())) {
            return chain.filter(exchange);
        }

        // 登录身份认证的逻辑
        log.info("需要认证的URL:{}",currentUrl);
        List<HttpCookie> access_token = exchange.getRequest().getCookies().get("access_token");
        if (access_token == null || access_token.size() == 0) {
            // 没有Token
            throw new GateWayException(SysRetCodeConstants.GET_TOKEN_FAILED.getCode(),
                    SysRetCodeConstants.GET_TOKEN_FAILED.getMessage());
        }
        String tokenStr = access_token.get(0).getValue();

        if (tokenStr == null || tokenStr.trim().length() == 0) {
            throw new GateWayException(SysRetCodeConstants.GET_TOKEN_FAILED.getCode(),
                    SysRetCodeConstants.GET_TOKEN_FAILED.getMessage());
        }

        // 获取Token字符串中的数据
        String userInfoJson = JwtTokenUtils.builder().token(tokenStr).build().freeJwt();

        // 将登录是放入JWT Token字符串中的用户信息，取出来，放入请求头中
        ServerWebExchange webExchange = putHeader(exchange, userInfoJson);
        return chain.filter(webExchange);
    }

    private ServerWebExchange putHeader(ServerWebExchange exchange, String userInfoJson) {
        ServerHttpRequest newServerRequest = exchange.getRequest()
                .mutate().header("user_info", userInfoJson).build();

        return exchange.mutate().request(newServerRequest).build();
    }

    /*
           返回false: 该请求需要被拦截，进行登录身份的验证
     */
    private boolean shouldSkip(String currentUrl, HttpMethod method) {

        // 验证登录，需要拦截
        if ("/user/login".equals(currentUrl.trim())
                && HttpMethod.GET.equals(method)) {
            return false;
        }
        //路径匹配器(简介SpringMvc拦截器的匹配器)
        //比如/oauth/** 可以匹配/oauth/token    /oauth/check_token等
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String skipPath : skillUrlProperties.getSkipUrls()) {
            if (pathMatcher.match(skipPath, currentUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
