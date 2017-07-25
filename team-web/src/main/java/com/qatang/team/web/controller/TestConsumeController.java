package com.qatang.team.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

/*    @Autowired
    private TestService testService;*/

    @RequestMapping("/test")
    public String test() {
        return restTemplate.getForEntity("http://TEAM-DATA-SERVICE/service/test", String.class).getBody();
    }

/*    @RequestMapping("/testFeign")
    public String testFeign() {
        return testService.test();
    }*/
}
