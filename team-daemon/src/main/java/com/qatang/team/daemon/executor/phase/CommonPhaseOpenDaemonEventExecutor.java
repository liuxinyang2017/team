package com.qatang.team.daemon.executor.phase;

import com.qatang.team.daemon.executor.AbstractDaemonEventExecutor;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通用彩期开启事件执行器
 * author: sunshow.
 */
@Component
public class CommonPhaseOpenDaemonEventExecutor extends AbstractDaemonEventExecutor {

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Override
    protected void executeTask(DaemonEventTask daemonEventTask) throws Exception {
        LotteryType lotteryType = daemonEventTask.getLotteryType();
        String phase = daemonEventTask.getPhase();

//        NumberLotteryData numberLotteryData = numberLotteryDataApiService.getByLotteryTypeAndPhase(lotteryType, phase);
//        if (numberLotteryData.getPhaseStatus() == PhaseStatus.OPEN
//                || numberLotteryData.getPhaseStatus() == PhaseStatus.CLOSED
//                || numberLotteryData.getPhaseStatus() == PhaseStatus.RESULT_SET
//                || numberLotteryData.getPhaseStatus() == PhaseStatus.RESULT_DETAIL_SET
//                || numberLotteryData.getPhaseStatus() == PhaseStatus.DISABLED) {
//            String msg = String.format("[%s]守护事件[%s]执行器: phase=%s 已经是[%s]状态, 无需再次更新", lotteryType.getName(), PhaseStatus.CLOSED.getName(), phase, numberLotteryData.getPhaseStatus().getName());
//            logger.error(msg);
//            return;
//        }
//
//        numberLotteryDataApiService.updateStatus(lotteryType, phase, PhaseStatus.OPEN, PhaseStatus.OPEN_NOT);
    }
}
