package com.qatang.team.fetcher.bean;

/**
 * 数字彩开奖详情抓取数据对象
 * @author qatang
 */
public class NumberLotteryFetchDetail {

    /**
     * 奖级key
     */
    private String prizeKey;

    /**
     * 奖级名称
     */
    private String prizeName;

    /**
     * 中奖注数
     */
    private Long prizeCount;

    /**
     * 每注金额
     */
    private Long prizeAmount;

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
