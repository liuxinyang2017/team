package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.exception.FetcherLogException;

/**
 * 抓取日志内部服务接口
 * @author wp
 * @since 2017/7/20
 */
public interface FetcherLogInternalService extends BaseInternalSerivce {

    /**
     * 创建抓取日志
     * @param fetcherLog 抓取日志
     * @return 保存后的抓取日志
     * @throws FetcherLogException 异常
     */
    FetcherLog save(FetcherLog fetcherLog) throws FetcherLogException;

    /**
     * 分页查询抓取日志
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 抓取日志
     * @throws FetcherLogException 异常
     */
    ApiResponse<FetcherLog> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetcherLogException;

    /**
     * 按id获取抓取日志
     * @param id 编码
     * @return 抓取日志
     * @throws FetcherLogException 异常
     */
    FetcherLog get(Long id) throws FetcherLogException;

    /**
     * 修改抓取日志
     * @param fetcherLog
     * @return
     * @throws FetcherLogException
     */
    FetcherLog update(FetcherLog fetcherLog) throws FetcherLogException;
}
