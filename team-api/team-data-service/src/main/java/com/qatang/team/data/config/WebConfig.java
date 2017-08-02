package com.qatang.team.data.config;

import com.qatang.team.json.CustomObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


/**
 * web应用配置类
 * 由框架扫描装载，取代web.xml,applicationContext.xml
 * servlet、filter映射顺序同声明顺序
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(new CustomObjectMapper()));
        converters.add(new StringHttpMessageConverter());
    }
}
