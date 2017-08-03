package com.qatang.team.fetcher.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.exception.ProxyDataException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/**
 * @author wangzhiliang
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/proxyData")
public interface ProxyDataApiService {

    /**
     * 创建代理数据
     * @param proxyData 代理数据
     * @return 保存后的代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ProxyData create(@RequestBody ProxyData proxyData) throws ProxyDataException;

    /**
     * 分页查询代理数据
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<ProxyData> findAll(@RequestBody PageableWrapper pageableWrapper) throws ProxyDataException;

    /**
     * 按id获取代理数据
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ProxyData get(@RequestParam("id") Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 待测试
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateWaitingTestStatus", method = RequestMethod.GET)
    ProxyData updateWaitingTestStatus(@RequestParam("id") Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 测试中
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateTestingStatus", method = RequestMethod.GET)
    ProxyData updateTestingStatus(@RequestParam("id") Long id) throws ProxyDataException;

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
    ProxyData updatePassStatus(@RequestParam("id") Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 测试失败
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateFailedStatus", method = RequestMethod.GET)
    ProxyData updateFailedStatus(@RequestParam("id") Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 已作废
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateObsoletedStatus", method = RequestMethod.GET)
    ProxyData updateObsoletedStatus(@RequestParam("id") Long id) throws ProxyDataException;

    /**
     * 更新开始测试时间
     * @param id 编码
     * @param beginTestTime 开始测试时间
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateBeginTestTime", method = RequestMethod.GET)
    ProxyData updateBeginTestTime(@RequestParam("id") Long id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("beginTestTime") LocalDateTime beginTestTime) throws ProxyDataException;

    /**
     * 更新jieshu测试时间
     * @param id 编码
     * @param endTestTime 结束测试时间
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/updateEndTestTime", method = RequestMethod.GET)
    ProxyData updateEndTestTime(@RequestParam("id") Long id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("endTestTime") LocalDateTime endTestTime) throws ProxyDataException;

    /***
     * 根据代理地址，代理端口获取代理数据
     * @param host 代理地址
     * @param port 代理端口
     * @return 获取到的代理数据
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/getByHostAndPort", method = RequestMethod.POST)
    ProxyData getByHostAndPort(@RequestParam("host") String host, @RequestParam("port") int port) throws ProxyDataException;

    /**
     * 加分
     * @param id 编码
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/increaseScore", method = RequestMethod.GET)
    void increaseScore(@RequestParam("id") Long id) throws ProxyDataException;

    /**
     * 减分
     * @param id 编码
     * @throws ProxyDataException 异常
     */
    @RequestMapping(value = "/decreaseScore", method = RequestMethod.GET)
    void decreaseScore(@RequestParam("id")Long id) throws ProxyDataException;

}
