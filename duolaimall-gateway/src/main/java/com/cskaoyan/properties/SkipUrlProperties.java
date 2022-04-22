package com.cskaoyan.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;


@Data
@ConfigurationProperties("cskaoyan.mall")
public class SkipUrlProperties {

    private LinkedHashSet<String> skipUrls;
}
