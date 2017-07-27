package com.qatang.team.fetcher.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
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

    /***
     * 创建代理验证日志
     * @param proxyValidateLog 代理验证日志对象
     * @return 保存后的代理验证日志
     * @throws ProxyValidateLogException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ProxyValidateLog create(@RequestBody ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;

    /***
     * 修改代理验证日志
     * @param proxyValidateLog 代理验证日志对象
     * @return 修改后的代理验证日志
     * @throws ProxyValidateLogException 异常
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ProxyValidateLog update(@RequestBody ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;

    /***
     * 通过id获取代理验证日志
     * @param id 代理日志id
     * @return 获取的代理验证日志
     * @throws ProxyValidateLogException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ProxyValidateLog get(@RequestParam("id") Long id) throws ProxyValidateLogException;

    /***
     * 自定义查询代理验证日志信息
     * @param pageableWrapper 自定义查询条件
     * @return 自定义查询代理日志列表信息
     * @throws ProxyValidateLogException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<ProxyValidateLog> findAll(@RequestBody PageableWrapper pageableWrapper) throws ProxyValidateLogException;
}
