package com.qatang.team.proxy.bean;

import java.net.Proxy;

/**
 * 代理信息
 * @author qatang
 */
public class ProxyInfo {
    /**
     * 代理类型
     */
    private Proxy.Type proxyType;

    /**
     * 代理地址
     */
    private String host;

    /**
     * 代理端口
     */
    private int port;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    public Proxy.Type getProxyType() {
        return proxyType;
    }

    public void setProxyType(Proxy.Type proxyType) {
        this.proxyType = proxyType;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
