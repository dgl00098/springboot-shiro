package com.dgl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    //地址映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //参数一:虚拟路径
        //参数二:本地的实体路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/usr/local/upload/");
    }

}