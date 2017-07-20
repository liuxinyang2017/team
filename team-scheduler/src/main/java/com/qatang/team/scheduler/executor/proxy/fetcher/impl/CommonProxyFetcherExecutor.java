package com.qatang.team.scheduler.executor.proxy.fetcher.impl;

import com.google.common.collect.Lists;
import com.qatang.team.core.util.CoreCollectionUtils;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import com.qatang.team.proxy.fetcher.IProxyFetcher;
import com.qatang.team.proxy.fetcher.ProxyFetcherFactory;
import com.qatang.team.scheduler.constant.Constants;
import com.qatang.team.scheduler.executor.proxy.fetcher.AbstractProxyFetcherExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qatang
 */
@Component
public class CommonProxyFetcherExecutor extends AbstractProxyFetcherExecutor {

    @Override
    public void executeFetcher() {
        String sourceName = StringUtils.join(Constants.proxyFetcherTypeList.stream().map(ProxyFetcherType::getName).collect(Collectors.toList()), ",");
        logger.info(String.format("开始执行代理抓取定时，代理来源：%s", sourceName));
        List<ProxyInfo> allProxyInfoList = Lists.newArrayList();
        for (ProxyFetcherType proxyFetcherType : Constants.proxyFetcherTypeList) {
            IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(proxyFetcherType);
            if (proxyFetcher == null) {
                logger.error(String.format("代理抓取定时：未找到(%s)的代理抓取器，跳过该来源代理抓取", proxyFetcherType.getName()));
                continue;
            }
            allProxyInfoList.addAll(proxyFetcher.fetchProxyList());
        }

        if (allProxyInfoList.isEmpty()) {
            logger.error(String.format("代理抓取定时：未抓取到任何代理，代理来源：%s", sourceName));
            return;
        }
        logger.info(String.format("代理抓取定时：抓取到代理总数，size=%s", allProxyInfoList.size()));

        // 去重
        allProxyInfoList = CoreCollectionUtils.distinctByKey(allProxyInfoList, ProxyInfo::getUrlStr);
        logger.info(String.format("代理抓取定时：抓取到代理去重后数量，size=%s", allProxyInfoList.size()));

        // 入库
        allProxyInfoList.forEach(proxyInfo -> {
            // 按照host和port查询，如果存在，return

            ProxyData proxyData = new ProxyData();
            proxyData.setHost(proxyInfo.getHost());
            proxyData.setPort(proxyInfo.getPort());
            proxyData.setUsername(proxyInfo.getUsername());
            proxyData.setPassword(proxyInfo.getPassword());
            proxyData.setProxyValidateStatus(ProxyValidateStatus.INIT);
            proxyData.setFailedCount(0);
            proxyData.setScore(0);

            try {
                // save


                logger.info(String.format("代理抓取定时：代理入库成功，%s", proxyInfo.getUrlStr()));
            } catch (Exception e) {
                logger.error(String.format("代理抓取定时：代理入库失败，%s", proxyInfo.getUrlStr()));
                logger.error(e.getMessage(), e);
            }
        });
    }
}
