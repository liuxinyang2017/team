package com.qatang.team.scheduler.executor.generator.phase;

import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.generator.phase.bean.PhaseInfo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * @author qatang
 */
public class StartPhaseInfoBuilder {

    public static PhaseInfo build(LotteryType lotteryType) {
        PhaseInfo phaseInfo = null;
        switch (lotteryType) {
            case FC_SSQ:
                phaseInfo = ssqBuild(lotteryType);
                break;
            default:
                break;
        }

        return phaseInfo;
    }

    private static PhaseInfo ssqBuild(LotteryType lotteryType) {
        int currentYear = LocalDateTime.now().getYear();

        PhaseInfo startPhaseInfo = new PhaseInfo();
        startPhaseInfo.setLotteryType(lotteryType);
        startPhaseInfo.setPhase(currentYear + "001");

        LocalDateTime firstDay = LocalDateTime.of(currentYear, 1, 1, 20, 0, 0);
        DayOfWeek dayOfWeek = firstDay.getDayOfWeek();

        switch (dayOfWeek) {
            case SUNDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(3));
                startPhaseInfo.setCloseTime(firstDay);
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
                break;
            case MONDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(1));
                startPhaseInfo.setCloseTime(firstDay.plusDays(1));
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
                break;
            case TUESDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(2));
                startPhaseInfo.setCloseTime(firstDay);
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
                break;
            case WEDNESDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(1));
                startPhaseInfo.setCloseTime(firstDay.plusDays(1));
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
            case THURSDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(2));
                startPhaseInfo.setCloseTime(firstDay);
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
            case FRIDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(1));
                startPhaseInfo.setCloseTime(firstDay.plusDays(2));
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
            case SATURDAY:
                startPhaseInfo.setOpenTime(firstDay.minusDays(2));
                startPhaseInfo.setCloseTime(firstDay.plusDays(1));
                startPhaseInfo.setPrizeTime(startPhaseInfo.getCloseTime().plusMinutes(75));
        }
        return startPhaseInfo;
    }
}
