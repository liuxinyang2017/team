package com.qatang.team.data.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.enums.daemon.DaemonEventStatus;

/**
 * @author jinsheng
 */
public interface DaemonEventTaskInternalService extends BaseInternalSerivce {

    /**
     * 新建守护事件任务对象
     * @param daemonEventTask 守护事件任务对象
     * @return 守护事件任务对象
     * @throws DaemonEventTaskException
     */
    DaemonEventTask save(DaemonEventTask daemonEventTask) throws DaemonEventTaskException;

    /**
     * 更新守护事件任务对象
     * @param daemonEventTask 守护事件任务对象
     * @return 守护事件任务对象
     * @throws DaemonEventTaskException
     */
    DaemonEventTask update(DaemonEventTask daemonEventTask) throws DaemonEventTaskException;

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

    /**
     * 更新赛程事件状态
     * @param id 主键
     * @param toStatus 目标状态
     * @param checkStatus 检查状态
     * @return 守护事件任务对象
     * @throws DaemonEventTaskException
     */
    DaemonEventTask updateStatus(Long id, DaemonEventStatus toStatus, DaemonEventStatus checkStatus) throws DaemonEventTaskException;
}
