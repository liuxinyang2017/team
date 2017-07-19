package com.qatang.team.scheduler.executor.proxy.fetcher.impl;

import com.google.common.collect.Lists;
import com.qatang.team.core.util.CoreCollectionUtils;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import com.qatang.team.proxy.fetcher.IProxyFetcher;
import com.qatang.team.proxy.fetcher.ProxyFetcherFactory;
import com.qatang.team.scheduler.executor.proxy.fetcher.AbstractProxyFetcherExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qatang
 */
public class CommonProxyFetcherExecutor extends AbstractProxyFetcherExecutor {
    private final static List<ProxyFetcherType> proxyFetcherTypeList = Lists.newArrayList(
            ProxyFetcherType.P_XICI,
            ProxyFetcherType.P_KUAI,
            ProxyFetcherType.P_CZ88
    );

    @Override
    public void executeFetcher() {
        String sourceName = StringUtils.join(proxyFetcherTypeList.stream().map(ProxyFetcherType::getName).collect(Collectors.toList()), ",");
        logger.info(String.format("开始执行代理抓取定时，代理来源：%s", sourceName));
        List<ProxyInfo> allProxyInfoList = Lists.newArrayList();
        for (ProxyFetcherType proxyFetcherType : proxyFetcherTypeList) {
            IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(proxyFetcherType);
            if (proxyFetcher == null) {
                logger.info(String.format("代理抓取定时：未找到(%s)的代理抓取器，跳过该来源代理抓取", proxyFetcherType.getName()));
                continue;
            }
            allProxyInfoList.addAll(proxyFetcher.fetchProxyList());
        }

        if (allProxyInfoList.isEmpty()) {
            logger.error(String.format("代理抓取定时：未抓取到任何代理，代理来源：%s", sourceName));
            return;
        }
        logger.error(String.format("代理抓取定时：抓取到代理总数，size=%s", allProxyInfoList.size()));

        // 去重
        allProxyInfoList = CoreCollectionUtils.distinctByKey(allProxyInfoList, ProxyInfo::getUrlStr);
        logger.error(String.format("代理抓取定时：抓取到代理去重后数量a，size=%s", allProxyInfoList.size()));
    }
}
