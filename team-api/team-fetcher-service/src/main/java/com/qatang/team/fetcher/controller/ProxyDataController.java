package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.service.ProxyDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzhiliang
 */
@RestController
@RequestMapping("/fetcher/proxyData")
public class ProxyDataController extends BaseController {
    @Autowired
    private ProxyDataInternalService proxyDataInternalService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ProxyData create(@RequestBody ProxyData proxyData) {

        logger.info("开始创建代理数据对象");
        //ProxyData proxyDataResult = proxyDataInternalService.save(proxyData);
        return new ProxyData();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody ProxyData proxyData) {
        logger.info("删除创建代理数据对象, proxyDataId={}", proxyData.getId());
        //proxyDataService.delete(proxyData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ProxyData update(@RequestBody ProxyData proxyData) {
        logger.info("修改代理数据对象");
        //ProxyData proxyData = proxyDataService.update(proxyData);
        return new ProxyData();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ProxyData get(@RequestParam(name = "id") Long id) {
        logger.info("获取代理数据对象信息");
        //ProxyData proxyData = proxyDataInternalService.get(id);
        return new ProxyData();
    }

/*    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<ProxyData> find(@RequestBody ProxyDataSearchable proxyDataSearchable) {
        logger.info("自定义查询代理数据对象信息");
        //return proxyDataService.find(apiRequest, apiRequestPage);
        return null;
    }*/
}
