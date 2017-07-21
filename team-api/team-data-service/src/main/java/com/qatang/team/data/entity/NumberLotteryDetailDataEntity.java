package com.qatang.team.data.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import com.qatang.team.enums.lottery.LotteryType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 数字彩开奖详情
 * @author wangzhiliang
 */
@Entity
@Table(name = "number_lottery_detail_data")
public class NumberLotteryDetailDataEntity implements BaseEntity {
    private static final long serialVersionUID = -8159275832892571426L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lottery_data_id", nullable = false)
    private Long lotteryDataId;

    /**
     * 彩种
     */
    @Column(name = "lottery_type", nullable = false)
    @Convert(converter = LotteryTypeConverter.class)
    private LotteryType lotteryType;

    /**
     * 彩期
     */
    private String phase;

    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    /**
     * 开奖详情奖级
     */
    @Column(name = "prize_key", nullable = false)
    private String prizeKey;

    /**
     * 开奖详情奖项名称
     */
    @Column(name = "prize_name", nullable = false)
    private String prizeName;

    /**
     * 中奖注数
     */
    @Column(name = "prize_count", nullable = false)
    private Long prizeCount;

    /**
     * 每注金额
     */
    @Column(name = "prize_amount", nullable = false)
    private Long prizeAmount;

    /**
     * 排序优先级，数字越大，优先级越高
     */
    private Integer priority;

    @PrePersist
    public void onCreate() {
        if (this.getCreatedTime() == null) {
            createdTime = LocalDateTime.now();
        }
        if (this.getUpdatedTime() == null) {
            updatedTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedTime = LocalDateTime.now();
    }

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

    public Long getLotteryDataId() {
        return lotteryDataId;
    }

    public void setLotteryDataId(Long lotteryDataId) {
        this.lotteryDataId = lotteryDataId;
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
