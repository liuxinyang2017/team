package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.exception.ProxyValidateLogException;

/**
 * 代理日志内部服务接口
 * @author wp
 * @since 2017/7/24
 */
public interface ProxyValidateLogInternalService extends BaseInternalSerivce {

    /**
     * 创建代理日志
     * @param proxyValidateLog 代理日志
     * @return 保存后的代理日志
     * @throws ProxyValidateLogException 异常
     */
    ProxyValidateLog save(ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;

    /**
     * 分页查询代理日志
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 代理日志
     * @throws ProxyValidateLogException 异常
     */
    ApiResponse<ProxyValidateLog> findAll(ApiRequest request, ApiRequestPage requestPage) throws ProxyValidateLogException;

    /**
     * 按id获取代理日志
     * @param id 编码
     * @return 代理日志
     * @throws ProxyValidateLogException 异常
     */
    ProxyValidateLog get(Long id) throws ProxyValidateLogException;

    /**
     * 修改代理日志
     * @param proxyValidateLog
     * @return
     * @throws ProxyValidateLogException
     */
    ProxyValidateLog update(ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException;
}
