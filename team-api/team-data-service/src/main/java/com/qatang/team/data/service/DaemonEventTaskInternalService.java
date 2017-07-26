package com.qatang.team.data.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskException;

/**
 * @author jinsheng
 */
public interface DaemonEventTaskInternalService extends BaseInternalSerivce {

    /**
     * 守护事件任务对象
     * @param id 编码
     * @return 守护事件任务对象
     * @throws DaemonEventTaskException
     */
    DaemonEventTask get(Long id) throws DaemonEventTaskException;

    /**
     * 守护事件任务对象自定义查询
     * @param request 自定义组合查询条件
     * @param requestPage 分页和排序条件
     * @return 分页组织的守护事件任务对象查询列表
     * @throws DaemonEventTaskException
     */
    ApiResponse<DaemonEventTask> findAll(ApiRequest request, ApiRequestPage requestPage) throws DaemonEventTaskException;
}
