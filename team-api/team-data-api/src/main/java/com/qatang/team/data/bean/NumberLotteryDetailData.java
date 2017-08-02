package com.qatang.team.data.bean;

import com.qatang.team.core.annotation.request.RequestApiBean;
import com.qatang.team.core.annotation.request.RequestApiFieldUpdatable;
import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.lottery.LotteryType;

import java.time.LocalDateTime;

/**
 * 数字彩彩果
 * @author qatang
 */
@RequestApiBean
public class NumberLotteryDetailData extends AbstractBaseApiBean {
    private static final long serialVersionUID = 9140489116564265451L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 开奖结果对象编码
     */
    private Long lotteryDataId;

    /**
     * 彩种
     */
    private LotteryType lotteryType;

    /**
     * 彩期
     */
    private String phase;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 奖级key
     */
    private String prizeKey;

    /**
     * 奖级名称
     */
    @RequestApiFieldUpdatable
    private String prizeName;

    /**
     * 中奖注数
     */
    private Long prizeCount;

    /**
     * 每注金额
     */
    private Long prizeAmount;

    /**
     * 排序优先级，数字越大，优先级越高
     */
    @RequestApiFieldUpdatable
    private Integer priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLotteryDataId() {
        return lotteryDataId;
    }

    public void setLotteryDataId(Long lotteryDataId) {
        this.lotteryDataId = lotteryDataId;
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

    public String getPrizeKey() {
        return prizeKey;
    }

    public void setPrizeKey(String prizeKey) {
        this.prizeKey = prizeKey;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Long getPrizeCount() {
        return prizeCount;
    }

    public void setPrizeCount(Long prizeCount) {
        this.prizeCount = prizeCount;
    }

    public Long getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(Long prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
