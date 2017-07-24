package com.qatang.team.fetcher.entity;

import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.converter.YesNoStatusConverter;
import com.qatang.team.enums.converter.fetcher.ProxyValidatorTypeConverter;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 代理日志实体
 * @author wp
 * @since 2017/7/23
 */
@Entity
@Table(name = "proxy_validate_log")
@DynamicInsert
@DynamicUpdate
public class ProxyValidateLogEntity implements BaseEntity {
    private static final long serialVersionUID = -1848101899318861035L;

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
     * 代理验证器类型
     */
    @Column(name = "proxy_validator_type", nullable = false)
    @Convert(converter = ProxyValidatorTypeConverter.class)
    private ProxyValidatorType proxyValidatorType;

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

    public ProxyValidatorType getProxyValidatorType() {
        return proxyValidatorType;
    }

    public void setProxyValidatorType(ProxyValidatorType proxyValidatorType) {
        this.proxyValidatorType = proxyValidatorType;
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
