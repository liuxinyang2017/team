package com.qatang.team.scheduler;

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
@EnableFeignClients({"com.qatang.team"})
public class SchedulerApplication implements CommandLineRunner {
    protected static final transient Logger logger = LoggerFactory.getLogger(SchedulerApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SchedulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.error("team scheduler start!");
        Thread.currentThread().join();
    }

    @PreDestroy
    private void onDestroy() {
        logger.error("team scheduler stop!");
    }
}
