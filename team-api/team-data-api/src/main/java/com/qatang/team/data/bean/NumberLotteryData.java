package com.qatang.team.data.bean;

import com.qatang.team.core.annotation.request.RequestApiBean;
import com.qatang.team.core.annotation.request.RequestApiFieldUpdatable;
import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

import java.time.LocalDateTime;

/**
 * 数字彩彩果
 * @author qatang
 */
@RequestApiBean
public class NumberLotteryData extends AbstractBaseApiBean {
    private static final long serialVersionUID = 5745385863292450844L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 彩种
     */
    private LotteryType lotteryType;

    /**
     * 彩期
     */
    private String phase;

    /**
     * 彩期状态
     */
    @RequestApiFieldUpdatable
    private PhaseStatus phaseStatus;

    /**
     * 是否为当前彩期
     */
    private YesNoStatus isCurrent;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 开启时间
     */
    @RequestApiFieldUpdatable
    private LocalDateTime openTime;

    /**
     * 关闭时间
     */
    @RequestApiFieldUpdatable
    private LocalDateTime closeTime;

    /**
     * 开奖时间
     */
    @RequestApiFieldUpdatable
    private LocalDateTime prizeTime;

    /**
     * 彩果
     * 01,02,03,04,05,06|07
     */
    @RequestApiFieldUpdatable
    private String result;

    /**
     * 号码获取时间
     */
    private LocalDateTime resultTime;

    /**
     * 详情获取时间
     */
    private LocalDateTime resultDetailTime;

    /**
     * 奖池金额
     */
    @RequestApiFieldUpdatable
    private Long poolAmount;

    /**
     * 销售总金额
     */
    @RequestApiFieldUpdatable
    private Long saleAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PhaseStatus getPhaseStatus() {
        return phaseStatus;
    }

    public void setPhaseStatus(PhaseStatus phaseStatus) {
        this.phaseStatus = phaseStatus;
    }

    public YesNoStatus getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(YesNoStatus isCurrent) {
        this.isCurrent = isCurrent;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(LocalDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public LocalDateTime getResultDetailTime() {
        return resultDetailTime;
    }

    public void setResultDetailTime(LocalDateTime resultDetailTime) {
        this.resultDetailTime = resultDetailTime;
    }

    public Long getPoolAmount() {
        return poolAmount;
    }

    public void setPoolAmount(Long poolAmount) {
        this.poolAmount = poolAmount;
    }

    public Long getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Long saleAmount) {
        this.saleAmount = saleAmount;
    }
}
