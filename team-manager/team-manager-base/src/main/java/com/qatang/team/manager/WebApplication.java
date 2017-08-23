package com.qatang.team.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动入口
 * @author jinsheng
 * @since 2016-04-26 16:47
 */
@SpringBootApplication
public class WebApplication {

    private static Logger logger = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) {
        try {
            logger.info("准备启动web应用");
            SpringApplication.run(WebApplication.class, args);
            logger.info("web应用启动成功");
        } catch (Exception e) {
            logger.error("web应用启动失败", e);
        }
    }
}
