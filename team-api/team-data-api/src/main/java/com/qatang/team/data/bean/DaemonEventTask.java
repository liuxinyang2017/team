package com.qatang.team.data.bean;


import com.qatang.team.core.annotation.request.RequestApiBean;
import com.qatang.team.core.annotation.request.RequestApiFieldUpdatable;
import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;

import java.util.Date;

/**
 * 守护事件任务对象
 * @author qatang
 * @since 2016-04-12 10:49
 */
@RequestApiBean
public class DaemonEventTask extends AbstractBaseApiBean {

    private static final long serialVersionUID = -8871432345159068237L;

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
     * 比赛编码 或 比赛序号（兼容北单）
     */
    private String matchNum;

    /**
     * 守护事件类型
     */
    private DaemonEventType type;

    /**
     * 守护事件状态
     */
    private DaemonEventStatus status;

    /**
     * 任务的预期执行时间
     */
    @RequestApiFieldUpdatable
    private Date executeTime;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updatedTime;

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

    public String getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    public DaemonEventType getType() {
        return type;
    }

    public void setType(DaemonEventType type) {
        this.type = type;
    }

    public DaemonEventStatus getStatus() {
        return status;
    }

    public void setStatus(DaemonEventStatus status) {
        this.status = status;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
