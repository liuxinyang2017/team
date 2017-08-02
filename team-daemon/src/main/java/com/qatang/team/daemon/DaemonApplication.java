package com.qatang.team.daemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import javax.annotation.PreDestroy;

/**
 * @author qatang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(value = {"com.qatang.team"})
public class DaemonApplication implements CommandLineRunner {
    protected static final transient Logger logger = LoggerFactory.getLogger(DaemonApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DaemonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.error("team daemon start!");
        Thread.currentThread().join();
    }

    @PreDestroy
    private void onDestroy() {
        logger.error("team daemon stop!");
    }
}
