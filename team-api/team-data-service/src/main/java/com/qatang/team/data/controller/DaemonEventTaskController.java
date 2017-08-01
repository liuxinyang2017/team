package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.bean.QDaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskInternalService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<DaemonEventTask> findAll(@RequestBody PageableWrapper pageableWrapper) {
        logger.info("开始守护事件任务对象自定义查询");
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        for (ApiRequestFilter filter : apiRequest.getFilterList()) {
            String field = filter.getField();
            Object value = filter.getValue();
            List<Object> valueList = filter.getValueList();

            switch (field) {
                case QDaemonEventTask.lotteryType:
                    if (value != null) {
                        value = LotteryType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> LotteryType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QDaemonEventTask.status:
                    if (value != null) {
                        value = DaemonEventStatus.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> DaemonEventStatus.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QDaemonEventTask.type:
                    if (value != null) {
                        value = DaemonEventType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> DaemonEventType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QDaemonEventTask.createdTime:
                case QDaemonEventTask.updatedTime:
                case QDaemonEventTask.executeTime:
                default:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
            }

        }
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
