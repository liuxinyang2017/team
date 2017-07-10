package com.qatang.team.proxy.fetcher;

import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;

import java.util.List;

/**
 * 代理抓取器
 * @author qatang
 */
public interface IProxyFetcher {

    /**
     * 代理抓取
     * @return 代理列表
     * @throws ProxyException 异常
     */
    List<ProxyInfo> fetchProxyList() throws ProxyException;
}
