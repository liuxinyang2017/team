package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.exception.ProxyDataException;

import java.time.LocalDateTime;

/**
 * 代理数据内部服务接口
 * @author wangzhiliang
 */
public interface ProxyDataInternalService extends BaseInternalSerivce {

    /**
     * 创建代理数据
     * @param proxyData 代理数据
     * @return 保存后的代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData save(ProxyData proxyData) throws ProxyDataException;

    /**
     * 分页查询代理数据
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ApiResponse<ProxyData> findAll(ApiRequest request, ApiRequestPage requestPage) throws ProxyDataException;

    /**
     * 按id获取代理数据
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData get(Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 待测试
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updateWaitingTestStatus(Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 测试中
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updateTestingStatus(Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 已通过
     * 连续失败次数清零
     * 初始化得分
     *
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updatePassStatus(Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 测试失败
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updateFailedStatus(Long id) throws ProxyDataException;

    /**
     * 将代理数据检测状态更新为 已作废
     * @param id 编码
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updateObsoletedStatus(Long id) throws ProxyDataException;

    /**
     * 更新开始测试时间
     * @param id 编码
     * @param beginTestTime 开始测试时间
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updateBeginTestTime(Long id, LocalDateTime beginTestTime) throws ProxyDataException;

    /**
     * 更新jieshu测试时间
     * @param id 编码
     * @param endTestTime 结束测试时间
     * @return 代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData updateEndTestTime(Long id, LocalDateTime endTestTime) throws ProxyDataException;
}
