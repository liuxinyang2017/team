package com.qatang.team.fetcher.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.converter.YesNoStatusConverter;
import com.qatang.team.enums.converter.fetcher.FetcherTypeConverter;
import com.qatang.team.enums.fetcher.FetcherType;
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
    @Column(name = "proxy_id", nullable = false)
    private Long proxyId;

    /**
     * 代理地址
     */
    @Column(name = "host", nullable = false)
    private String host;

    /**
     * 代理端口
     */
    @Column(name = "port", nullable = false)
    private int port;

    /**
     * 抓取器类型
     */
    @Column(name = "fetcher_type", nullable = false)
    @Convert(converter = FetcherTypeConverter.class)
    private FetcherType fetcherType;

    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    /**
     * 开始测试时间
     */
    @Column(name = "begin_test_time")
    private LocalDateTime beginTestTime;

    /**
     * 结束测试时间
     */
    @Column(name = "end_test_time")
    private LocalDateTime endTestTime;

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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getBeginTestTime() {
        return beginTestTime;
    }

    public void setBeginTestTime(LocalDateTime beginTestTime) {
        this.beginTestTime = beginTestTime;
    }

    public LocalDateTime getEndTestTime() {
        return endTestTime;
    }

    public void setEndTestTime(LocalDateTime endTestTime) {
        this.endTestTime = endTestTime;
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
