package com.qatang.team.fetcher.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.converter.fetcher.FetcherTypeConverter;
import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author wp
 * @since 2017/7/23
 */
@Entity
@Table(name = "number_lottery_fetch_detail_data")
@DynamicInsert
@DynamicUpdate
public class NumberLotteryFetchDetailDataEntity implements BaseEntity {
    private static final long serialVersionUID = 4421987708633857018L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 抓取结果编码
     */
    @Column(name = "fetch_result_id", nullable = false)
    private Long fetchResultId;

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
     * 彩种
     */
    @Column(name = "lottery_type", nullable = false)
    @Convert(converter = LotteryTypeConverter.class)
    private LotteryType lotteryType;

    /**
     * 彩期
     */
    @Column(name = "phase", nullable = false)
    private String phase;

    /**
     * 抓取器
     */
    @Column(name = "fetcher_type", nullable = false)
    @Convert(converter = FetcherTypeConverter.class)
    private FetcherType fetcherType;

    /**
     * 抓取到数据的时间
     */
    @Column(name = "fetched_time")
    private LocalDateTime fetchedTime;

    /**
     * 奖级key
     */
    @Column(name = "prize_key")
    private String prizeKey;

    /**
     * 奖级名称
     */
    @Column(name = "prize_name")
    private String prizeName;

    /**
     * 中奖注数
     */
    @Column(name = "prize_count")
    private Long prizeCount;

    /**
     * 每注金额
     */
    @Column(name = "prize_amount")
    private Long prizeAmount;

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

    public Long getFetchResultId() {
        return fetchResultId;
    }

    public void setFetchResultId(Long fetchResultId) {
        this.fetchResultId = fetchResultId;
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

    public FetcherType getFetcherType() {
        return fetcherType;
    }

    public void setFetcherType(FetcherType fetcherType) {
        this.fetcherType = fetcherType;
    }

    public LocalDateTime getFetchedTime() {
        return fetchedTime;
    }

    public void setFetchedTime(LocalDateTime fetchedTime) {
        this.fetchedTime = fetchedTime;
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
}
