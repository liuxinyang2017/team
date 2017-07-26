package com.qatang.team.data.controller;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.bean.QDaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskApiService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.lottery.LotteryType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jinsheng
 */
public class DaemonEventTaskControllerTest extends AbstractControllerTest {
    @Autowired
    private DaemonEventTaskApiService daemonEventTaskApiService;

    @Test
    public void testGet() {
        Long id = 1L;
        DaemonEventTask daemonEventTask = daemonEventTaskApiService.get(id);
        logger.info("守护事件任务对象[id={}]彩期为：{}", id, daemonEventTask.getPhase());
    }

    @Test
    public void testFind() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        DaemonEventStatus status = DaemonEventStatus.PENDING;

        ApiRequest request = ApiRequest.newInstance();
        request.filterEqual(QDaemonEventTask.lotteryType, lotteryType);
        request.filterEqual(QDaemonEventTask.status, status);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(0, 100);
        requestPage.addOrder(QDaemonEventTask.executeTime);
        requestPage.addOrder(QDaemonEventTask.id);

        PageableWrapper pageableWrapper = new PageableWrapper(request, requestPage);

        ApiResponse<DaemonEventTask> response = daemonEventTaskApiService.find(pageableWrapper);
        logger.info("守护事件任务对象查询总数：{}", response.getTotal());
    }
}
