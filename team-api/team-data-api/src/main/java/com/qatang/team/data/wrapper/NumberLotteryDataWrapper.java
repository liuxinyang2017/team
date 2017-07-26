package com.qatang.team.data.wrapper;

import com.qatang.team.core.wrapper.BaseWrapper;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

/**
 * 数字彩彩果对象数据
 * @author jinsheng
 */
public class NumberLotteryDataWrapper implements BaseWrapper {
    private static final long serialVersionUID = -3776988026790708292L;
    private Long id;
    private LotteryType lotteryType;
    private String phase;
    private PhaseStatus toStatus;
    private PhaseStatus checkStatus;

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

    public PhaseStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(PhaseStatus toStatus) {
        this.toStatus = toStatus;
    }

    public PhaseStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(PhaseStatus checkStatus) {
        this.checkStatus = checkStatus;
    }
}
