package com.qatang.team.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qatang.team.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangzhiliang
 */
@RestController
@RequestMapping("/consume")
public class TestConsumeController {
    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private TestService testService;

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public String test() {
        return restTemplate.getForEntity("http://TEAM-DATA-SERVICE/service/test", String.class).getBody();
    }

    @RequestMapping("/testFeign")
    public String testFeign() {
//        return testService.test();
        return null;
    }

    @HystrixCommand(fallbackMethod = "testFallback")
    @RequestMapping(value = "/testHystrix1", method = RequestMethod.GET)
    public String testHystrix1() {
        return testService.testService();
    }

    public String testFallback() {
        return "fallback";
    }



    @RequestMapping(value = "/testHystrix2", method = RequestMethod.GET)
    public String testHystrixCommand() throws Exception {
        return new TestCommand(restTemplate).execute();
        //return testService.testService();
    }
}
