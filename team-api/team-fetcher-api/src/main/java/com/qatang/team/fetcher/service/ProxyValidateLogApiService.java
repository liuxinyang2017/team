package com.qatang.team.fetcher.service;

import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.exception.ProxyValidateLogException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 代理验证日志Api
 * @author wp
 * @since 2017/7/23
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/proxyDataLog")
public interface ProxyValidateLogApiService {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ProxyValidateLog create(@RequestBody ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ProxyValidateLog update(@RequestBody ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ProxyValidateLog get(@RequestParam(name = "id") Long id) throws ProxyValidateLogException;
}
