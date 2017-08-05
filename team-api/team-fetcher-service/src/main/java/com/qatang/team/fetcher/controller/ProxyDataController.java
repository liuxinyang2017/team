package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.fetcher.exception.ProxyDataException;
import com.qatang.team.fetcher.service.ProxyDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public ProxyData create(@RequestBody ProxyData proxyData) throws ProxyDataException {
        logger.info("开始创建代理数据对象");
        return proxyDataInternalService.save(proxyData);
    }

    /**
     * 分页查询代理数据
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<ProxyData> findAll(@RequestBody PageableWrapper pageableWrapper) throws ProxyDataException {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询代理数据信息");
        for (ApiRequestFilter filter : apiRequest.getFilterList()) {
            String field = filter.getField();
            Object value = filter.getValue();
            List<Object> valueList = filter.getValueList();

            switch (field) {
                case QProxyData.proxyValidateStatus:
                    if (value != null) {
                        value = ProxyValidateStatus.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> ProxyValidateStatus.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QProxyData.createdTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QProxyData.updatedTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QProxyData.beginTestTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QProxyData.endTestTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                default:
            }
        }
        return proxyDataInternalService.findAll(apiRequest, apiRequestPage);
    }

    /**
     * 按id获取代理数据
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ProxyData get(@RequestParam Long id) throws ProxyDataException {
        logger.info("按id获取代理数据，id：[{}]", id);
        return proxyDataInternalService.get(id);
    }

    /**
     * 将代理数据检测状态更新为 待测试
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateWaitingTestStatus", method = RequestMethod.GET)
    public ProxyData updateWaitingTestStatus(@RequestParam Long id) throws ProxyDataException {
        logger.info("将代理数据检测状态更新为 待测试,id:[{}]", id);
        return proxyDataInternalService.updateWaitingTestStatus(id);
    }

    /**
     * 将代理数据检测状态更新为 测试中
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateTestingStatus", method = RequestMethod.GET)
    public ProxyData updateTestingStatus(@RequestParam Long id) throws ProxyDataException {
        logger.info("将代理数据检测状态更新为 测试中,id:[{}]", id);
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
    public ProxyData updatePassStatus(@RequestParam Long id) throws ProxyDataException {
        logger.info("将代理数据检测状态更新为 已通过,id:[{}]", id);
        return proxyDataInternalService.updatePassStatus(id);
    }

    /**
     * 将代理数据检测状态更新为 测试失败
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateFailedStatus", method = RequestMethod.GET)
    public ProxyData updateFailedStatus(@RequestParam Long id) throws ProxyDataException {
        logger.info("将代理数据检测状态更新为 测试失败,id:[{}]", id);
        return proxyDataInternalService.updateFailedStatus(id);
    }

    /**
     * 将代理数据检测状态更新为 已作废
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateObsoletedStatus", method = RequestMethod.GET)
    public ProxyData updateObsoletedStatus(@RequestParam Long id) throws ProxyDataException {
        logger.info("将代理数据检测状态更新为 已作废,id:[{}]", id);
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
    public ProxyData updateBeginTestTime(@RequestParam Long id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime beginTestTime) throws ProxyDataException {
        logger.info("更新开始测试时间,id:[{}],开始测试时间：[{}]", id, CoreDateUtils.formatLocalDateTime(beginTestTime));
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
    public ProxyData updateEndTestTime(@RequestParam Long id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endTestTime) throws ProxyDataException {
        logger.info("更新结束测试时间,id:[{}],结束测试时间：[{}]", id, CoreDateUtils.formatLocalDateTime(endTestTime));
        return proxyDataInternalService.updateEndTestTime(id, endTestTime);
    }

    /***
     * 根据代理地址，代理端口获取代理数据
     * @param host 代理地址
     * @param port 代理端口
     * @return 获取到的代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/getByHostAndPort", method = RequestMethod.POST)
    ProxyData getByHostAndPort(@RequestParam("host") String host, @RequestParam("port") int port) throws ProxyDataException {
        logger.info("根据代理地址,代理端口获取代理数据：代理地址[{}], 代理端口[{}]", host, port);
        return proxyDataInternalService.getByHostAndPort(host, port);
    }

    /**
     * 加分
     * @param id 编码
     * @param score 分数
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/increaseScore", method = RequestMethod.GET)
    void increaseScore(@RequestParam("id") Long id, @RequestParam("score")int score) throws ProxyDataException {
        logger.info("根据id加分操作：id[{}],分数[{}]", id, score);
        proxyDataInternalService.increaseScore(id, score);
    }

    /**
     * 减分
     * @param id 编码
     * @param score 分数
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/decreaseScore", method = RequestMethod.GET)
    void decreaseScore(@RequestParam("id")Long id, @RequestParam("score")int score) throws ProxyDataException {
        logger.info("根据id减分操作：id[{}], 分数", id, score);
        proxyDataInternalService.decreaseScore(id, score);
    }
}
