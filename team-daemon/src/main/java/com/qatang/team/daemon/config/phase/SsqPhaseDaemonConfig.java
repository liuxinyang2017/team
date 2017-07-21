package com.qatang.team.daemon.config.phase;

import com.google.common.collect.Sets;
import com.qatang.team.core.thread.ThreadContainer;
import com.qatang.team.daemon.handler.IDaemonHandler;
import com.qatang.team.daemon.handler.phase.CommonPhaseDaemonHandler;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * 双色球彩期守护
 * Created by qatang on 7/28/15.
 */
@Configuration
public class SsqPhaseDaemonConfig {

    private static Set<DaemonEventType> daemonEventTypeSet = Sets.newHashSet();
    static {
        daemonEventTypeSet.add(DaemonEventType.PHASE_OPEN);
        daemonEventTypeSet.add(DaemonEventType.PHASE_CLOSE);
        daemonEventTypeSet.add(DaemonEventType.PHASE_CURRENT_SWITCH);
    }

    @Bean
    public IDaemonHandler ssqPhaseDaemonHandler() {
        return new CommonPhaseDaemonHandler(LotteryType.FC_SSQ, daemonEventTypeSet);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ThreadContainer ssqPhaseDaemonHandlerContainer() {
        ThreadContainer threadContainer = new ThreadContainer(ssqPhaseDaemonHandler(), "双色球彩期守护线程");
        threadContainer.setBeforeRunWaitTime(5 * 1000L);
        return threadContainer;
    }
}
