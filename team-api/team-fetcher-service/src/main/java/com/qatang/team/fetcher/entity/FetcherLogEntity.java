package com.qatang.team.fetcher.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.converter.YesNoStatusConverter;
import com.qatang.team.enums.converter.fetcher.FetcherDataTypeConverter;
import com.qatang.team.enums.converter.fetcher.FetcherTypeConverter;
import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * 抓取日志实体
 * @author wp
 * @since 2017/7/20
 */
@Entity
@Table(name = "fetcher_log")
@DynamicInsert
@DynamicUpdate
public class FetcherLogEntity implements BaseEntity {

    private static final long serialVersionUID = -204837903596727645L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 代理编码
     */
    @Column(name = "proxy_id")
    private Long proxyId;

    /**
     * 代理地址
     */
    @Column(name = "host")
    private String host;

    /**
     * 代理端口
     */
    @Column(name = "port")
    private int port;

    /**
     * 抓取器类型
     */
    @Column(name = "fetcher_type", nullable = false)
    @Convert(converter = FetcherTypeConverter.class)
    private FetcherType fetcherType;

    /**
     * 彩种类型
     */
    @Column(name = "lottery_type", nullable = false)
    @Convert(converter = LotteryTypeConverter.class)
    private LotteryType lotteryType;

    /**
     * 抓取数据类型
     */
    @Column(name = "fetcher_data_type", nullable = false)
    @Convert(converter = FetcherDataTypeConverter.class)
    private FetcherDataType fetcherDataType;

    /**
     * 彩期
     */
    @Column(name = "phase")
    private String phase;

    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    /**
     * 开始抓取时间
     */
    @Column(name = "begin_fetch_time")
    private LocalDateTime beginFetchTime;

    /**
     * 结束抓取时间
     */
    @Column(name = "end_fetch_time")
    private LocalDateTime endFetchTime;

    /**
     * 耗时，单位：毫秒
     */
    @Column(name = "spent_mills")
    private Long spentMills;

    /**
     * 是否成功
     */
    @Column(name = "success", nullable = false)
    @Convert(converter = YesNoStatusConverter.class)
    private YesNoStatus success;

    /**
     * 响应信息
     */
    @Column(name = "message")
    private String message;

    @PrePersist
    public void onCreate() {
        if (this.getCreatedTime() == null) {
            createdTime = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProxyId() {
        return proxyId;
    }

    public void setProxyId(Long proxyId) {
        this.proxyId = proxyId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public FetcherType getFetcherType() {
        return fetcherType;
    }

    public void setFetcherType(FetcherType fetcherType) {
        this.fetcherType = fetcherType;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public FetcherDataType getFetcherDataType() {
        return fetcherDataType;
    }

    public void setFetcherDataType(FetcherDataType fetcherDataType) {
        this.fetcherDataType = fetcherDataType;
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

    public LocalDateTime getBeginFetchTime() {
        return beginFetchTime;
    }

    public void setBeginFetchTime(LocalDateTime beginFetchTime) {
        this.beginFetchTime = beginFetchTime;
    }

    public LocalDateTime getEndFetchTime() {
        return endFetchTime;
    }

    public void setEndFetchTime(LocalDateTime endFetchTime) {
        this.endFetchTime = endFetchTime;
    }

    public Long getSpentMills() {
        return spentMills;
    }

    public void setSpentMills(Long spentMills) {
        this.spentMills = spentMills;
    }

    public YesNoStatus getSuccess() {
        return success;
    }

    public void setSuccess(YesNoStatus success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
