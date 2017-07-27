package com.qatang.team.fetcher.bean;

import com.qatang.team.core.annotation.request.RequestApiBean;
import com.qatang.team.core.annotation.request.RequestApiFieldUpdatable;
import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;

import java.time.LocalDateTime;

/**
 * 代理验证日志
 * @author qatang
 */
@RequestApiBean
public class ProxyValidateLog extends AbstractBaseApiBean {
    private static final long serialVersionUID = 2771227063335205275L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 代理编码
     */
    private Long proxyId;

    /**
     * 代理地址
     */
    private String host;

    /**
     * 代理端口
     */
    private int port;

    /**
     * 代理验证器类型
     */
    private ProxyValidatorType proxyValidatorType;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 开始测试时间
     */
    private LocalDateTime beginTestTime;

    /**
     * 结束测试时间
     */
    private LocalDateTime endTestTime;

    /**
     * 耗时，单位：毫秒
     */
    private Long spentMills;

    /**
     * 是否成功
     */
    @RequestApiFieldUpdatable
    private YesNoStatus success;

    /**
     * 响应信息
     */
    private String message;

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
