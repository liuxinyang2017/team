package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
