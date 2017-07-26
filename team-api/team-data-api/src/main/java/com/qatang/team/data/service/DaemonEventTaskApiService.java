package com.qatang.team.data.service;


import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseApiService;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskDuplicatedException;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.data.wrapper.DaemonEventTaskWrapper;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 守护事件任务服务
 *
 * @author sunshow
 */
@FeignClient("team-data-service")
@RequestMapping("/data/daemonEventTask")
public interface DaemonEventTaskApiService extends BaseApiService {

    /**
     * 新建待执行的守护事件任务
     *
     * @param daemonEventTask 守护事件任务对象
     * @return 保存后的 守护事件任务对象
     * @throws DaemonEventTaskException
     * @throws DaemonEventTaskDuplicatedException
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    DaemonEventTask savePendingTask(@RequestBody DaemonEventTask daemonEventTask) throws DaemonEventTaskException, DaemonEventTaskDuplicatedException;

    /**
     * 修改守护事件任务
     *
     * @param daemonEventTask 守护事件任务对象
     * @return 守护事件任务
     * @throws DaemonEventTaskException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    DaemonEventTask update(@RequestBody DaemonEventTask daemonEventTask) throws DaemonEventTaskException;

    /**
     * 修改状态
     * @param daemonEventTaskId 守护事件任务ID
     * @param toStatus 要更新的状态
     * @param checkStatus 原始状态监测, 不检测传null
     * @return
     * @throws DaemonEventTaskException
     */
//    DaemonEventTask updateStatus(Long daemonEventTaskId, DaemonEventStatus toStatus, DaemonEventStatus checkStatus) throws DaemonEventTaskException;

    /**
     * 获取守护事件任务
     *
     * @param daemonEventTaskId 守护事件任务ID
     * @return 守护事件任务, 不存在返回null
     * @throws DaemonEventTaskException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    DaemonEventTask get(@RequestParam("id") Long daemonEventTaskId) throws DaemonEventTaskException;

    /**
     * 守护事件任务信息自定义查询
     * @param daemonEventTaskWrapper 守护事件任务对象数据
     * @return 分页组织的 守护事件任务信息查询列表
     * @throws DaemonEventTaskException
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<DaemonEventTask> find(@RequestBody DaemonEventTaskWrapper daemonEventTaskWrapper) throws DaemonEventTaskException;
}
