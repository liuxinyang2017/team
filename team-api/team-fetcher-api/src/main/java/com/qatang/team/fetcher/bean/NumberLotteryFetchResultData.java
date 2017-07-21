package com.qatang.team.fetcher.bean;

import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.lottery.LotteryType;

import java.time.LocalDateTime;

/**
 * 数字彩开奖结果抓取数据对象
 * @author qatang
 */
public class NumberLotteryFetchResultData extends AbstractBaseApiBean {
    private static final long serialVersionUID = 2771227063335205275L;

    /**
     * 主键
     */
    private Long id;

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
     * 彩果
     * 01,02,03,04,05,06|07
     */
    private String result;

    /**
     * 奖池金额
     */
    private Long poolAmount;

    /**
     * 销售总金额
     */
    private Long saleAmount;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
