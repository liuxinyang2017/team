package com.qatang.team.proxy;

import com.google.common.collect.Lists;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.selector.FetcherProxySelector;

import java.net.ProxySelector;
import java.util.List;

/**
 * @author qatang
 */
public class ProxyManager {
    private final static List<ProxyInfo> proxyInfoList = Lists.newArrayList(
            new ProxyInfo("120.236.178.117", 8118),
            new ProxyInfo("218.104.148.157", 8080)
    );
    public static void start() {
        FetcherProxySelector fetcherProxySelector = new FetcherProxySelector(proxyInfoList);
        ProxySelector.setDefault(fetcherProxySelector);

        // 设置全局代理用户和密码，如果不同代理使用不同密码，还不知道咋解决
//        proxyInfoList.forEach(proxyInfo -> {
//            if (!Strings.isNullOrEmpty(proxyInfo.getUser())) {
//                Authenticator.setDefault(new Authenticator() {
//                    public PasswordAuthentication getPasswordAuthentication() {
//                        return (new PasswordAuthentication(proxyInfo.getUser(),
//                                proxyInfo.getPassword().toCharArray()));
//                    }
//                });
//            }
//        });
    }
}
