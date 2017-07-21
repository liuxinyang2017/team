package com.qatang.team.daemon.executor.phase;

import com.qatang.team.daemon.executor.AbstractDaemonEventExecutor;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通用彩期开启事件执行器
 * author: sunshow.
 */
@Component
public class CommonPhaseSwitchDaemonEventExecutor extends AbstractDaemonEventExecutor {

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Override
    protected void executeTask(DaemonEventTask daemonEventTask) throws Exception {
        LotteryType lotteryType = daemonEventTask.getLotteryType();

        numberLotteryDataApiService.switchCurrentPhase(lotteryType);
    }
}
