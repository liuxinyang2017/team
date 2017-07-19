package com.qatang.team.fetcher.bean;

import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

import java.time.LocalDateTime;

/**
 * 代理数据
 * @author qatang
 */
public class ProxyData extends AbstractBaseApiBean {
    private static final long serialVersionUID = 2771227063335205275L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 代理地址
     * host port 联合唯一
     */
    private String host;

    /**
     * 代理端口
     */
    private int port;

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
    private ProxyValidateStatus proxyValidateStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 开始测试时间
     */
    private LocalDateTime beginTestTime;

    /**
     * 结束测试时间
     */
    private LocalDateTime endTestTime;

    /**
     * 得分
     */
    private Integer score;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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
}
