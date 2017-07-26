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
     * 获取守护事件任务对象
     * @param id 守护事件任务对象id
     * @return 守护事件任务对象
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    DaemonEventTask get(@RequestParam Long id) {
        logger.info("获取守护事件任务对象信息");
        DaemonEventTask daemonEventTask = daemonEventTaskInternalService.get(id);
        return daemonEventTask;
    }

    /**
     * 守护事件任务对象自定义查询
     * @param pageableWrapper 自定义组合查询条件、分页和排序条件
     * @return 分页组织的守护事件任务对象信息查询列表
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<DaemonEventTask> find(@RequestBody PageableWrapper pageableWrapper) {
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
    DaemonEventTask updateStatus(@RequestParam("id") Long daemonEventTaskId, @RequestParam("toStatus") DaemonEventStatus toStatus, @RequestParam("checkStatus") DaemonEventStatus checkStatus) {
        return daemonEventTaskInternalService.updateStatus(daemonEventTaskId, toStatus, checkStatus);
    }
}
