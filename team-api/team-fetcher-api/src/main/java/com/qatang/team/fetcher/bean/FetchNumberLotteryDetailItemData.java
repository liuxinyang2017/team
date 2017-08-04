package com.qatang.team.fetcher.bean;

import com.qatang.team.core.annotation.request.RequestApiBean;
import com.qatang.team.core.annotation.request.RequestApiFieldUpdatable;
import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;

import java.time.LocalDateTime;

/**
 * 数字彩开奖详情抓取数据对象
 * @author qatang
 */
@RequestApiBean
public class FetchNumberLotteryDetailItemData extends AbstractBaseApiBean {
    private static final long serialVersionUID = 2771227063335205275L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 抓取结果编码
     */
    private Long fetchDetailId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 彩种
     */
    private LotteryType lotteryType;

    /**
     * 彩期
     */
    private String phase;

    /**
     * 抓取器
     */
    private FetcherType fetcherType;

    /**
     * 抓取到数据的时间
     */
    private LocalDateTime fetchedTime;

    /**
     * 奖级key
     */
    @RequestApiFieldUpdatable
    private String prizeKey;

    /**
     * 奖级名称
     */
    @RequestApiFieldUpdatable
    private String prizeName;

    /**
     * 中奖注数
     */
    @RequestApiFieldUpdatable
    private Long prizeCount;

    /**
     * 每注金额
     */
    @RequestApiFieldUpdatable
    private Long prizeAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
