package com.qatang.team.data.controller;

import com.qatang.team.core.request.ApiRequestOrder;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.bean.QDaemonEventTask;
import com.qatang.team.data.service.DaemonEventTaskApiService;
import com.qatang.team.data.wrapper.DaemonEventTaskWrapper;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.lottery.LotteryType;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

        ApiRequestOrder executeTimeOrder = new ApiRequestOrder(QDaemonEventTask.executeTime, PageOrderType.ASC);
        ApiRequestOrder idOrder = new ApiRequestOrder(QDaemonEventTask.id, PageOrderType.ASC);
        List<ApiRequestOrder> orderList = Lists.newArrayList(executeTimeOrder, idOrder);

        DaemonEventTaskWrapper daemonEventTaskWrapper = new DaemonEventTaskWrapper();
        daemonEventTaskWrapper.setPage(0);
        daemonEventTaskWrapper.setPageSize(100);
        daemonEventTaskWrapper.setLotteryType(lotteryType);
        daemonEventTaskWrapper.setStatus(status);
        daemonEventTaskWrapper.setOrderList(orderList);

        ApiResponse<DaemonEventTask> response = daemonEventTaskApiService.find(daemonEventTaskWrapper);
        logger.info("守护事件任务对象查询总数：{}", response.getTotal());
    }
}
