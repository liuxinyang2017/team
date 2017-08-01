package com.qatang.team.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qatang.team.json.CustomObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author sunshow
 */
@Configuration
@PropertySource({
        "classpath:application.properties",
})
@ComponentScan(basePackages = {"com.qatang.team.data"})
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

}
