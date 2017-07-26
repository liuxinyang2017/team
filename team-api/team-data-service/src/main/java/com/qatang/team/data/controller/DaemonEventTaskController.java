package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskInternalService;
import com.qatang.team.data.wrapper.DaemonEventTaskWrapper;
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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    DaemonEventTask get(@RequestParam Long id) {
        logger.info("获取守护事件任务对象信息");
        DaemonEventTask daemonEventTask = daemonEventTaskInternalService.get(id);
        return daemonEventTask;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<DaemonEventTask> find(@RequestBody DaemonEventTaskWrapper daemonEventTaskWrapper) {
        ApiRequest apiRequest = daemonEventTaskWrapper.convertRequest();
        ApiRequestPage apiRequestPage = daemonEventTaskWrapper.convertPageable();
        return daemonEventTaskInternalService.findAll(apiRequest, apiRequestPage);
    }
}
