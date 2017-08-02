package com.qatang.team.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author wangzhiliang
 */
@RestController
@RequestMapping("/service")
public class TestServiceController {

    //Hystrix 默认超时时间是2000毫秒
    @RequestMapping("/test")
    public String test() throws Exception {
        int sleepTime = new Random().nextInt(3000);
        System.out.println("sleepTime:" + sleepTime);
        Thread.sleep(sleepTime);
        return "test";
    }
}
