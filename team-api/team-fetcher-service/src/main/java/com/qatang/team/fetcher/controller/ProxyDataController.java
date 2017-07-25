package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.exception.ProxyDataException;
import com.qatang.team.fetcher.service.ProxyDataInternalService;
import com.qatang.team.fetcher.wrapper.ProxyDataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author wangzhiliang
 */
@RestController
@RequestMapping("/fetcher/proxyData")
public class ProxyDataController extends BaseController {
    @Autowired
    private ProxyDataInternalService proxyDataInternalService;

    /**
     * 创建代理数据
     * @param proxyData 代理数据
     * @return 保存后的代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ProxyData create(@RequestBody ProxyData proxyData) throws ProxyDataException {
        return proxyDataInternalService.save(proxyData);
    }

    /**
     * 分页查询代理数据
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<ProxyData> find(@RequestBody ProxyDataWrapper proxyDataWrapper) throws ProxyDataException {
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        return proxyDataInternalService.findAll(apiRequest, apiRequestPage);
    }

    /**
     * 按id获取代理数据
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ProxyData get(@RequestParam Long id) throws ProxyDataException {
        return proxyDataInternalService.get(id);
    }

    /**
     * 将代理数据检测状态更新为 待测试
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateWaitingTestStatus", method = RequestMethod.GET)
    ProxyData updateWaitingTestStatus(@RequestParam Long id) throws ProxyDataException {
        return proxyDataInternalService.updateWaitingTestStatus(id);
    }

    /**
     * 将代理数据检测状态更新为 测试中
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateTestingStatus", method = RequestMethod.GET)
    ProxyData updateTestingStatus(@RequestParam Long id) throws ProxyDataException {
        return proxyDataInternalService.updateTestingStatus(id);
    }


    /**
     * 将代理数据检测状态更新为 已通过
     * 连续失败次数清零
     * 初始化得分
     *
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updatePassStatus", method = RequestMethod.GET)
    ProxyData updatePassStatus(@RequestParam Long id) throws ProxyDataException {
        return proxyDataInternalService.updatePassStatus(id);
    }

    /**
     * 将代理数据检测状态更新为 测试失败
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateFailedStatus", method = RequestMethod.GET)
    ProxyData updateFailedStatus(@RequestParam Long id) throws ProxyDataException {
        return proxyDataInternalService.updateFailedStatus(id);
    }

    /**
     * 将代理数据检测状态更新为 已作废
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateObsoletedStatus", method = RequestMethod.GET)
    ProxyData updateObsoletedStatus(@RequestParam Long id) throws ProxyDataException {
        return proxyDataInternalService.updateObsoletedStatus(id);
    }

    /**
     * 更新开始测试时间
     * @param id 编码
     * @param beginTestTime 开始测试时间
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateBeginTestTime", method = RequestMethod.GET)
    ProxyData updateBeginTestTime(@RequestParam Long id, @RequestParam LocalDateTime beginTestTime) throws ProxyDataException {
        return proxyDataInternalService.updateBeginTestTime(id, beginTestTime);
    }

    /**
     * 更新jieshu测试时间
     * @param id 编码
     * @param endTestTime 结束测试时间
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateEndTestTime", method = RequestMethod.GET)
    ProxyData updateEndTestTime(@RequestParam Long id, @RequestParam LocalDateTime endTestTime) throws ProxyDataException {
        return proxyDataInternalService.updateEndTestTime(id, endTestTime);
    }
}
