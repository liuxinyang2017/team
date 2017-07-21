package com.qatang.team.generator.phase.worker.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.generator.phase.bean.PhaseInfo;
import com.qatang.team.generator.phase.exception.GeneratorException;
import com.qatang.team.generator.phase.worker.AbstractPhaseGenerator;
import org.apache.commons.lang3.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 双色球彩期生成
 * @author qatang
 */
public class SsqPhaseGenerator extends AbstractPhaseGenerator {
    private final LotteryType internalLotteryType = LotteryType.FC_SSQ;

    /**
     * 二、四、日开奖
     */
    private final Map<DayOfWeek, Integer> weekDayCount = ImmutableMap.of(
            DayOfWeek.TUESDAY, 2,
            DayOfWeek.THURSDAY, 3,
            DayOfWeek.SUNDAY, 2
    );

    @Override
    public List<PhaseInfo> generate(PhaseInfo startPhaseInfo, Integer count) throws GeneratorException {
        if (startPhaseInfo == null) {
            throw new GeneratorException(String.format("(%s)彩期生成异常：初始彩期信息不能为空", internalLotteryType.getName()));
        }

        LotteryType lotteryType = startPhaseInfo.getLotteryType();
        String startPhase = startPhaseInfo.getPhase();

        if (lotteryType == null || StringUtils.isEmpty(startPhase)) {
            throw new GeneratorException(String.format("(%s)彩期生成异常：彩种类型不能为空", internalLotteryType.getName()));
        }

        if (!lotteryType.equals(internalLotteryType)) {
            throw new GeneratorException(String.format("(%s)彩期生成异常：彩种类型不匹配，lotteryType=%s", internalLotteryType.getName(), lotteryType.getName()));
        }

        if (count == null || count <= 0) {
           count = defaultGenerateCount;
        }

        List<PhaseInfo> phaseInfoList = Lists.newArrayList();

        int i = 1;
        PhaseInfo currentPhase = startPhaseInfo;
        while (i <= count) {
            LocalDateTime previousOpenTime = currentPhase.getOpenTime();
            LocalDateTime previousCloseTime = currentPhase.getCloseTime();

            int plusDayCount = weekDayCount.get(previousCloseTime.getDayOfWeek());

            LocalDateTime currentOpenTime = previousCloseTime.plusSeconds(0);
            LocalDateTime currentCloseTime = currentOpenTime.plusDays(plusDayCount);
            LocalDateTime currentDrawTime = currentOpenTime.plusDays(plusDayCount).plusMinutes(75);

            int currentIntPhase = Integer.valueOf(currentPhase.getPhase()) + 1;

            // 跨元旦
            if (currentOpenTime.getYear() - currentCloseTime.getYear() != 0) {
                currentIntPhase = Integer.valueOf(currentCloseTime.format(DateTimeFormatter.ofPattern("yyyy")) + "001");
            }

            // 跨除夕
            if (CoreDateUtils.isDuringSpringFestival(currentCloseTime)) {
                currentCloseTime = currentOpenTime.plusDays(plusDayCount + 7);
                currentDrawTime = currentOpenTime.plusDays(plusDayCount + 7).plusMinutes(75);
            }

            PhaseInfo phaseInfo = new PhaseInfo();
            phaseInfo.setLotteryType(lotteryType);
            phaseInfo.setPhase(String.valueOf(currentIntPhase));
            phaseInfo.setOpenTime(currentOpenTime);
            phaseInfo.setCloseTime(currentCloseTime);
            phaseInfo.setPrizeTime(currentDrawTime);
            phaseInfoList.add(phaseInfo);

            currentPhase = phaseInfo;
            i ++;
        }
        return phaseInfoList;
    }
}
