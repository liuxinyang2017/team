package com.qatang.team.data.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.converter.YesNoStatusConverter;
import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import com.qatang.team.enums.converter.lottery.PhaseStatusConverter;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 数字彩彩果
 * @author qatang
 */
@Entity
@Table(name = "number_lottery_data")
@DynamicInsert
@DynamicUpdate
public class NumberLotteryDataEntity implements BaseEntity {
    private static final long serialVersionUID = -942772883229708585L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 彩期状态
     */
    @Column(name = "phase_status", nullable = false)
    @Convert(converter = PhaseStatusConverter.class)
    private PhaseStatus phaseStatus;

    /**
     * 是否为当前彩期
     */
    @Column(name = "is_current", nullable = false)
    @Convert(converter = YesNoStatusConverter.class)
    private YesNoStatus isCurrent;

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
     * 开启时间
     */
    @Column(name = "open_time", nullable = false)
    private LocalDateTime openTime;

    /**
     * 关闭时间
     */
    @Column(name = "close_time", nullable = false)
    private LocalDateTime closeTime;

    /**
     * 开奖时间
     */
    @Column(name = "prize_time", nullable = false)
    private LocalDateTime prizeTime;

    /**
     * 彩果
     * 01,02,03,04,05,06|07
     */
    private String result;

    /**
     * 号码获取时间
     */
    @Column(name = "result_time", nullable = false)
    private LocalDateTime resultTime;

    /**
     * 详情获取时间
     */
    @Column(name = "result_detail_time", nullable = false)
    private LocalDateTime resultDetailTime;

    /**
     * 奖池金额
     */
    @Column(name = "pool_amount")
    private Long poolAmount;

    /**
     * 销售总金额
     */
    @Column(name = "sale_amount")
    private Long saleAmount;

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
