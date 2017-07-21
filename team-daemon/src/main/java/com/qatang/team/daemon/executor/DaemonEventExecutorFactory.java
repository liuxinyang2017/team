package com.qatang.team.daemon.executor;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.qatang.team.daemon.executor.phase.CommonPhaseCloseDaemonEventExecutor;
import com.qatang.team.daemon.executor.phase.CommonPhaseOpenDaemonEventExecutor;
import com.qatang.team.daemon.executor.phase.CommonPhaseSwitchDaemonEventExecutor;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * author: sunshow.
 */
@Component
public class DaemonEventExecutorFactory {

    @Autowired
    private CommonPhaseOpenDaemonEventExecutor commonPhaseOpenDaemonEventExecutor;

    @Autowired
    private CommonPhaseCloseDaemonEventExecutor commonPhaseCloseDaemonEventExecutor;

    @Autowired
    private CommonPhaseSwitchDaemonEventExecutor commonPhaseSwitchDaemonEventExecutor;

    private Table<LotteryType, DaemonEventType, IDaemonEventExecutor> phaseDaemonEventExecutorBinding = HashBasedTable.create();

    @PostConstruct
    private void init() {
        phaseDaemonEventExecutorBinding.put(LotteryType.DEFAULT, DaemonEventType.PHASE_OPEN, commonPhaseOpenDaemonEventExecutor);
        phaseDaemonEventExecutorBinding.put(LotteryType.DEFAULT, DaemonEventType.PHASE_CLOSE, commonPhaseCloseDaemonEventExecutor);
        phaseDaemonEventExecutorBinding.put(LotteryType.DEFAULT, DaemonEventType.PHASE_CURRENT_SWITCH, commonPhaseSwitchDaemonEventExecutor);
    }

    public IDaemonEventExecutor getExecutor(LotteryType lotteryType, DaemonEventType daemonEventType) {
        if (!phaseDaemonEventExecutorBinding.contains(lotteryType, daemonEventType)) {
            return phaseDaemonEventExecutorBinding.get(LotteryType.DEFAULT, daemonEventType);
        }
        return phaseDaemonEventExecutorBinding.get(lotteryType, daemonEventType);
    }
}
