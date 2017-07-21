package com.qatang.team.fetcher.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.converter.fetcher.ProxyValidateStatusConverter;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 代理数据
 * @author wangzhiliang
 */
@Entity
@Table(name = "proxy_data")
@DynamicInsert
@DynamicUpdate
public class ProxyDataEntity implements BaseEntity {

    private static final long serialVersionUID = -7251911721071264275L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 代理地址
     * host port 联合唯一
     */
    @Column(nullable = false)
    private String host;

    /**
     * 代理端口
     */
    @Column(nullable = false)
    private Integer port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 代理验证状态
     */
    @Column(name = "proxy_validate_status", nullable = false)
    @Convert(converter = ProxyValidateStatusConverter.class)
    private ProxyValidateStatus proxyValidateStatus;

    /**
     * 连续检测失败次数
     */
    @Column(name = "failed_count")
    private Integer failedCount;

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
     * 得分
     */
    private Integer score;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProxyValidateStatus getProxyValidateStatus() {
        return proxyValidateStatus;
    }

    public void setProxyValidateStatus(ProxyValidateStatus proxyValidateStatus) {
        this.proxyValidateStatus = proxyValidateStatus;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }
}
