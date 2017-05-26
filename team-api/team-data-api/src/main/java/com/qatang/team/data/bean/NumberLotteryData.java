package com.qatang.team.data.bean;

import com.qatang.team.enums.lottery.LotteryType;

import java.time.LocalDateTime;

/**
 * 数字彩彩果
 * @author qatang
 */
public class NumberLotteryData {
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
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 开奖时间
     */
    private LocalDateTime prizeTime;

    /**
     * 彩果
     * 01,02,03,04,05,06|07
     */
    private String result;
}
