package com.qatang.team.data.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.bean.QDaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskApiService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jinsheng
 */
public class DaemonEventTaskControllerTest extends AbstractControllerTest {
    @Autowired
    private DaemonEventTaskApiService daemonEventTaskApiService;

    @Test
    public void testSave() {
        DaemonEventTask daemonEventTask = new DaemonEventTask();
        daemonEventTask.setLotteryType(LotteryType.FC_SSQ);
        daemonEventTask.setPhase("2017001");
        daemonEventTask.setMatchNum("2017001");
        daemonEventTask.setType(DaemonEventType.MATCH_OPEN);
        daemonEventTask.setStatus(DaemonEventStatus.PENDING);
        daemonEventTask.setExecuteTime(LocalDateTime.now());
        daemonEventTask = daemonEventTaskApiService.savePendingTask(daemonEventTask);
        Assert.assertTrue(daemonEventTask.getId() != null);
    }

    @Test
    public void testUpdate() {
        DaemonEventTask daemonEventTask = daemonEventTaskApiService.get(1L);
        daemonEventTask.setLotteryType(LotteryType.FC_SSQ);
        daemonEventTask.setPhase("2017002");
        daemonEventTask.setMatchNum("2017002");
        daemonEventTask.setType(DaemonEventType.MATCH_OPEN);
        daemonEventTask.setStatus(DaemonEventStatus.EXECUTING);
        daemonEventTask.setExecuteTime(LocalDateTime.now());
        daemonEventTask = daemonEventTaskApiService.update(daemonEventTask);
        Assert.assertTrue(daemonEventTask.getStatus().equals(DaemonEventStatus.PENDING));
    }
    
    @Test
    public void testGet() {
        Long id = 1L;
        DaemonEventTask daemonEventTask = daemonEventTaskApiService.get(id);
        logger.info("守护事件任务对象[id={}]彩期为：{}", id, daemonEventTask.getPhase());
    }

    @Test
    public void testFindAll() {
        LotteryType lotteryType = LotteryType.FC_SSQ;

        ApiRequest request = ApiRequest.newInstance();
        request.filterEqual(QDaemonEventTask.lotteryType, lotteryType);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(0, 10);
        requestPage.addOrder(QDaemonEventTask.createdTime);
        requestPage.addOrder(QDaemonEventTask.id);

        PageableWrapper pageableWrapper = new PageableWrapper(request, requestPage);

        ApiResponse<DaemonEventTask> response = daemonEventTaskApiService.findAll(pageableWrapper);
        logger.info("彩种[{}]守护事件任务对象查询总数：{}", lotteryType.getName(), response.getTotal());
        List<DaemonEventTask> daemonEventTaskList = Lists.newArrayList(response.getPagedData());
        daemonEventTaskList.forEach(daemonEventTask -> logger.info("彩期：{}", daemonEventTask.getPhase()));
    }

    @Test
    public void testUpdateStatus() {
        Long id = 1L;
        DaemonEventStatus toStatus = DaemonEventStatus.EXECUTED;
        DaemonEventStatus checkStatus = DaemonEventStatus.EXECUTING;
        DaemonEventTask daemonEventTask = daemonEventTaskApiService.updateStatus(id, toStatus, checkStatus);
        logger.info("守护事件任务对象[id={}]的赛程事件状态为：{}", daemonEventTask.getId(), daemonEventTask.getStatus().getName());
    }
}
