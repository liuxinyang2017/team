package com.qatang.team.daemon.config;

import com.qatang.team.core.annotation.exception.ExceptionLoader;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangzhiliang
 */
@Configuration
@ComponentScan(basePackages = "com.qatang.team", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(value = {ExceptionLoader.class}))
public class ClientExceptionConfig {

}
