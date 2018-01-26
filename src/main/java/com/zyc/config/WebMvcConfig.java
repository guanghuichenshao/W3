package com.zyc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    //当访问主页的时候，我去转向我自己写的功能Controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/welcome/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers( registry );
    }
    //放行静态内容，对动态内容进行编译
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/").
                resourceChain(true);

    }
}
