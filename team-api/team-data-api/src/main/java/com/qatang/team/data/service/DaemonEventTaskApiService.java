package com.qatang.team.data.service;


import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseApiService;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskDuplicatedException;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.enums.daemon.DaemonEventStatus;

/**
 * 守护事件任务服务
 *
 * @author sunshow
 */
public interface DaemonEventTaskApiService extends BaseApiService {

    /**
     * 新建待执行的守护事件任务
     *
     * @param daemonEventTask 守护事件任务对象
     * @return 保存后的 守护事件任务对象
     * @throws DaemonEventTaskException
     * @throws DaemonEventTaskDuplicatedException
     */
    DaemonEventTask savePendingTask(DaemonEventTask daemonEventTask) throws DaemonEventTaskException, DaemonEventTaskDuplicatedException;

    /**
     * 修改守护事件任务
     *
     * @param daemonEventTask 守护事件任务对象
     * @return 守护事件任务
     * @throws DaemonEventTaskException
     */
    DaemonEventTask update(DaemonEventTask daemonEventTask) throws DaemonEventTaskException;

    /**
     * 修改状态
     * @param daemonEventTaskId 守护事件任务ID
     * @param toStatus 要更新的状态
     * @param checkStatus 原始状态监测, 不检测传null
     * @return
     * @throws DaemonEventTaskException
     */
    DaemonEventTask updateStatus(Long daemonEventTaskId, DaemonEventStatus toStatus, DaemonEventStatus checkStatus) throws DaemonEventTaskException;

    /**
     * 获取守护事件任务
     *
     * @param daemonEventTaskId 守护事件任务ID
     * @return 守护事件任务, 不存在返回null
     * @throws DaemonEventTaskException
     */
    DaemonEventTask get(Long daemonEventTaskId) throws DaemonEventTaskException;

    /**
     * 守护事件任务信息自定义查询
     *
     * @param request     自定义组合查询条件
     * @param requestPage 分页和排序条件
     * @return 分页组织的 守护事件任务信息查询列表
     * @throws DaemonEventTaskException
     */
    ApiResponse<DaemonEventTask> findAll(ApiRequest request, ApiRequestPage requestPage) throws DaemonEventTaskException;

}