package com.qatang.generator.phase.worker;

import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.generator.phase.bean.PhaseInfo;
import com.qatang.team.generator.phase.worker.IPhaseGenerator;
import com.qatang.team.generator.phase.worker.impl.SsqPhaseGenerator;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qatang
 */
public class SsqPhaseGeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        IPhaseGenerator phaseGenerator = new SsqPhaseGenerator();

        PhaseInfo startPhaseInfo = new PhaseInfo();
        startPhaseInfo.setLotteryType(LotteryType.FC_SSQ);
        startPhaseInfo.setPhase("2017001");
        startPhaseInfo.setOpenTime(LocalDateTime.of(2016, 12, 29, 20, 0, 0));
        startPhaseInfo.setCloseTime(LocalDateTime.of(2017, 1, 1, 20, 0, 0));
        startPhaseInfo.setPrizeTime(LocalDateTime.of(2017, 1, 1, 21, 15, 0));

        List<PhaseInfo> phaseInfoList = phaseGenerator.generate(startPhaseInfo, 30);
        phaseInfoList.forEach(phaseInfo -> {
            String sb = phaseInfo.getLotteryType().getName() +
                    "," +
                    phaseInfo.getPhase() +
                    "," +
                    CoreDateUtils.formatLocalDateTime(phaseInfo.getOpenTime(), "yyyy-MM-dd HH:mm:ss") +
                    "," +
                    CoreDateUtils.formatLocalDateTime(phaseInfo.getCloseTime(), "yyyy-MM-dd HH:mm:ss") +
                    "," +
                    CoreDateUtils.formatLocalDateTime(phaseInfo.getPrizeTime(), "yyyy-MM-dd HH:mm:ss");
            System.out.println(sb);
        });

    }
}
