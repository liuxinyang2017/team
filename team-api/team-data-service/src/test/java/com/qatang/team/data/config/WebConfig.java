package com.qatang.team.data.config;

import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jinsheng
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(new LotteryTypeConverter());
    }
}
