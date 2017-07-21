package com.qatang.team.fetcher.service;

import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.exception.ProxyDataException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangzhiliang
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/proxyData")
public interface ProxyDataApiService {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ProxyData create(@RequestBody ProxyData proxyData) throws ProxyDataException;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody ProxyData proxyData) throws ProxyDataException;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ProxyData update(@RequestBody ProxyData proxyData) throws ProxyDataException;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ProxyData get(@RequestParam(name = "id") Long id) throws ProxyDataException;
}
