package com.qatang.team.manager.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author jinsheng
 * @since 2016-04-27 15:22
 */
@Configuration
@ComponentScan(basePackages = "com.qatang.team.manager",
               excludeFilters = @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class}))
public class BeanConfig {


}
