package com.qatang.team.generator.phase.bean;

import com.qatang.team.enums.lottery.LotteryType;

import java.time.LocalDateTime;

/**
 * 彩期信息
 * @author qatang
 */
public class PhaseInfo {

    /**
     * 彩种
     */
    public LotteryType lotteryType;

    /**
     * 彩期
     */
    public String phase;

    /**
     * 开启时间
     */
    private LocalDateTime openTime;

    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 开奖时间
     */
    private LocalDateTime prizeTime;

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public LocalDateTime getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(LocalDateTime prizeTime) {
        this.prizeTime = prizeTime;
    }
}
