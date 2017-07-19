package com.qatang.team.data.service.test;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangzhiliang
 */
@FeignClient("team-data-service")
@RequestMapping("/service")
public interface TestService {

    @RequestMapping("/test")
    String test();
}
