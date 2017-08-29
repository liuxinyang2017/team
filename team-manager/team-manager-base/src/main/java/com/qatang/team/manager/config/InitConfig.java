package com.qatang.team.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jinsheng
 * @since 2016-04-26 16:43
 */
@Configuration
@Import(value = {BeanConfig.class, JpaConfig.class, ShiroConfig.class})
public class InitConfig {

}
