package com.qatang.team.fetcher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qatang.team.json.CustomObjectMapper;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

/**
 * @author sunshow
 */
@Configuration
@PropertySource({
        "classpath:application.properties",
})
@ComponentScan(basePackages = {"com.qatang.team.fetcher"})
public class InitConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Primary
    public ObjectMapper customObjectMapper() {
        return new CustomObjectMapper();
    }

    /**
     * feign get方式请求入参LocalDateTime类型转换
     * @return
     */
    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
        return formatterRegistry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(formatterRegistry);
        };
    }
}
