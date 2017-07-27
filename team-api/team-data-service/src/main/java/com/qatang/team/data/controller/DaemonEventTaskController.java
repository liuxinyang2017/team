package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskInternalService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jinsheng
 */
@RestController
@RequestMapping("/data/daemonEventTask")
public class DaemonEventTaskController extends BaseController {
    @Autowired
    private DaemonEventTaskInternalService daemonEventTaskInternalService;

    /**
     * 新建待执行的守护事件任务
     * @param daemonEventTask 守护事件任务对象
     * @return 保存后的 守护事件任务对象
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public DaemonEventTask savePendingTask(@RequestBody DaemonEventTask daemonEventTask) {
        logger.info("开始新建待执行的守护事件任务");
        return daemonEventTaskInternalService.save(daemonEventTask);
    }

    /**
     * 修改守护事件任务
     * @param daemonEventTask 守护事件任务对象
     * @return 守护事件任务
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DaemonEventTask update(@RequestBody DaemonEventTask daemonEventTask) {
        logger.info("开始修改守护事件任务");
        return daemonEventTaskInternalService.update(daemonEventTask);
    }

    /**
     * 获取守护事件任务对象
     * @param id 守护事件任务对象id
     * @return 守护事件任务对象
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public DaemonEventTask get(@RequestParam("id") Long id) {
        logger.info("获取守护事件任务对象信息");
        return daemonEventTaskInternalService.get(id);
    }

    /**
     * 守护事件任务对象自定义查询
     * @param pageableWrapper 自定义组合查询条件、分页和排序条件
     * @return 分页组织的守护事件任务对象信息查询列表
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ApiResponse<DaemonEventTask> findAll(@RequestBody PageableWrapper pageableWrapper) {
        logger.info("开始守护事件任务对象自定义查询");
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        return daemonEventTaskInternalService.findAll(apiRequest, apiRequestPage);
    }

    /**
     * 更新守护事件状态
     * @param daemonEventTaskId 守护事件任务ID
     * @param toStatus 要更新的状态
     * @param checkStatus 原始状态监测, 不检测传null
     * @return 守护事件任务对象
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public DaemonEventTask updateStatus(@RequestParam("id") Long daemonEventTaskId, @RequestParam("toStatus") DaemonEventStatus toStatus, @RequestParam("checkStatus") DaemonEventStatus checkStatus) {
        logger.info("开始更新守护事件状态");
        return daemonEventTaskInternalService.updateStatus(daemonEventTaskId, toStatus, checkStatus);
    }
}
