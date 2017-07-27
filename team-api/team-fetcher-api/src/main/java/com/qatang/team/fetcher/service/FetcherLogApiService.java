package com.qatang.team.fetcher.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.exception.FetcherLogException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wp
 * @since 2017/7/20
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/fetcherLog")
public interface FetcherLogApiService {

    /***
     * 创建抓取日志
     * @param fetcherLog 抓取日志对象
     * @return 保存后的抓取日志对象
     * @throws FetcherLogException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    FetcherLog create(@RequestBody FetcherLog fetcherLog) throws FetcherLogException;

    /***
     * 修改抓取日志
     * @param fetcherLog 抓取日志对象
     * @return 修改后的抓取日志对象
     * @throws FetcherLogException 异常
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    FetcherLog update(@RequestBody FetcherLog fetcherLog) throws FetcherLogException;

    /***
     * 通过id查询抓取日志
     * @param id 对象id
     * @return 获取到的抓取日志信息
     * @throws FetcherLogException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    FetcherLog get(@RequestParam("id") Long id) throws FetcherLogException;

    /***
     * 自定义查询抓取日志信息
     * @param pageableWrapper 自定义查询条件
     * @return 查询到的抓取日志信息
     * @throws FetcherLogException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<FetcherLog> findAll(@RequestBody PageableWrapper pageableWrapper) throws FetcherLogException;
}
