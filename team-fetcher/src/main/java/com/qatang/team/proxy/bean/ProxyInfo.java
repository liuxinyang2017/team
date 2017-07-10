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
    private Proxy.Type proxyType = Proxy.Type.HTTP;

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
    private String username = "";

    /**
     * 密码
     */
    private String password = "";

    public ProxyInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ProxyInfo(Proxy.Type proxyType, String host, int port, String username, String password) {
        this.proxyType = proxyType;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public Proxy.Type getProxyType() {
        return proxyType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
