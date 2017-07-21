package com.qatang.team.daemon.handler;

import com.google.common.collect.Maps;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * author: sunshow.
 */
@Component
public class DaemonHandlerFactory {

    @Autowired
    @Qualifier("ssqPhaseDaemonHandler")
    private IDaemonHandler ssqPhaseDaemonHandler;

    private Map<LotteryType, IDaemonHandler> daemonHandlerMap = Maps.newHashMap();

    @PostConstruct
    private void init() {
        daemonHandlerMap.put(LotteryType.FC_SSQ, ssqPhaseDaemonHandler);
    }

    public IDaemonHandler get(LotteryType lotteryType) {
        return daemonHandlerMap.get(lotteryType);
    }
}
