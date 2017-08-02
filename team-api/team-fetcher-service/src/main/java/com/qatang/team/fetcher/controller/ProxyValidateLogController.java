package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.service.ProxyValidateLogInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wp
 * @since 2017/7/24
 */
@RestController
@RequestMapping("/fetcher/proxyDataLog")
public class ProxyValidateLogController extends BaseController {

    @Autowired
    private ProxyValidateLogInternalService proxyValidateLogInternalService;

    /***
     * 创建代理日志
     * @param proxyValidateLog 代理日志对象
     * @return 保存后的代理日志对象
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ProxyValidateLog create(@RequestBody ProxyValidateLog proxyValidateLog) {
        logger.info("开始创建代理日志对象");
        return proxyValidateLogInternalService.save(proxyValidateLog);
    }

    /***
     * 修改代理日志
     * @param proxyValidateLog 代理日志
     * @return 修改后的代理日志
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ProxyValidateLog update(@RequestBody ProxyValidateLog proxyValidateLog) {
        logger.info("修改代理日志对象,id:[{}]", proxyValidateLog.getId());
        return proxyValidateLogInternalService.update(proxyValidateLog);
    }

    /***
     * 根据id获取代理日志信息
     * @param id 代理编码
     * @return 获取到的代理日志信息
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ProxyValidateLog get(@RequestParam("id") Long id) {
        logger.info("获取代理日志对象信息,id:[{}]", id);
        return proxyValidateLogInternalService.get(id);
    }

    /***
     * 自定义查询代理日志信息
     * @param pageableWrapper 自定义查询条件
     * @return 获取到的代理日志信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<ProxyValidateLog> findAll(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询代理日志对象信息");
        return proxyValidateLogInternalService.findAll(apiRequest, apiRequestPage);
    }
}
