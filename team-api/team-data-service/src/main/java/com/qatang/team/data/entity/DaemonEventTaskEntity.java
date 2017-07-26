package com.qatang.team.data.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.converter.daemon.DaemonEventStatusConverter;
import com.qatang.team.enums.converter.daemon.DaemonEventTypeConverter;
import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jinsheng
 */
@Entity
@Table(name = "daemon_event_task")
@DynamicInsert
@DynamicUpdate
public class DaemonEventTaskEntity implements BaseEntity {
    private static final long serialVersionUID = 6185105892256934845L;

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
     * 比赛编码 或 比赛序号（兼容北单）
     */
    @Column(name = "match_num", nullable = false)
    private String matchNum;

    /**
     * 守护事件类型
     */
    @Column(name = "type", nullable = false)
    @Convert(converter = DaemonEventTypeConverter.class)
    private DaemonEventType type;

    /**
     * 守护事件状态
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = DaemonEventStatusConverter.class)
    private DaemonEventStatus status;

    /**
     * 任务的预期执行时间
     */
    @Column(name = "execute_time", nullable = false)
    private Date executeTime;

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
}
