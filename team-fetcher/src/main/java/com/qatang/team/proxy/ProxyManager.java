package com.qatang.team.proxy;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import com.qatang.team.proxy.fetcher.IProxyFetcher;
import com.qatang.team.proxy.fetcher.ProxyFetcherFactory;
import com.qatang.team.proxy.selector.FetcherProxySelector;
import com.qatang.team.proxy.validator.ProxyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ProxySelector;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author qatang
 */
public class ProxyManager {
    private final static Logger logger = LoggerFactory.getLogger(ProxyManager.class);

    private final static String VALIDATE_TARGET_URL = "http://www.baidu.com";
    private final static String VALIDATE_TARGET_ENCODING = "UTF-8";

    private final static String PROXY_INFO_LIST_KEY = "proxy_info_list_key";
    private final static List<ProxyFetcherType> proxyFetcherTypeList = Lists.newArrayList(
            ProxyFetcherType.P_XICI,
            ProxyFetcherType.P_KUAI,
            ProxyFetcherType.P_CZ88
    );

    private static LoadingCache<String, List<ProxyInfo>> commandExecutorLoadingCache = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build(new CacheLoader<String, List<ProxyInfo>>() {
                @Override
                public List<ProxyInfo> load(String key) throws Exception {
                    logger.error("代理缓存失效，开始抓取可用代理");
                    // 抓取所有代理
                    List<ProxyInfo> allProxyInfoList = Lists.newArrayList();
                    for (ProxyFetcherType proxyFetcherType : proxyFetcherTypeList) {
                        IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(proxyFetcherType);
                        allProxyInfoList.addAll(proxyFetcher.fetchProxyList());
                    }

                    if (allProxyInfoList.isEmpty()) {
                        logger.error("未抓取到任何代理");
                        throw new ProxyException("未抓取到任何代理");
                    }
                    logger.error(String.format("抓取所有代理，size=%s", allProxyInfoList.size()));

                    allProxyInfoList = allProxyInfoList.subList(0, 20);

                    // 测试代理速度
                    CountDownLatch latch = new CountDownLatch(allProxyInfoList.size());
                    ExecutorService executorService = Executors.newFixedThreadPool(10);
                    Map<ProxyInfo, Long> scoreProxyMap = Maps.newConcurrentMap();

                    try {
                        for (ProxyInfo proxyInfo : allProxyInfoList) {
                            executorService.submit(() -> {
                                try {
                                    long score = ProxyValidator.validate(proxyInfo, VALIDATE_TARGET_URL, VALIDATE_TARGET_ENCODING);
                                    scoreProxyMap.put(proxyInfo, score);
                                } catch (ProxyException e) {
                                    logger.error(e.getMessage(), e);
                                }
                                latch.countDown();
                            });
                        }
                        logger.error("等待测试代理结果");
                        latch.await();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    } finally {
                        executorService.shutdown();
                    }

                    if (scoreProxyMap.keySet().isEmpty()) {
                        logger.error("未抓取到任何代理");
                        throw new ProxyException("未抓取到任何代理");
                    }

                    // 排序
                    List<ProxyInfo> finalProxyInfoList = scoreProxyMap.keySet().stream().sorted(Comparator.comparing(scoreProxyMap::get)).collect(Collectors.toList());
                    logger.error(String.format("完成代理测试，有效可用代理size=%s", finalProxyInfoList.size()));
                    return finalProxyInfoList;
                }
            });

    public static void update() {
        List<ProxyInfo> proxyInfoList = Lists.newArrayList();
        try {
            proxyInfoList = commandExecutorLoadingCache.get(PROXY_INFO_LIST_KEY);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
        }

        if (proxyInfoList.isEmpty()) {
            logger.error("未抓取到任何可用代理，直接退出抓取");
            return;
        }
        logger.error(String.format("获取到有效可用代理size=%s", proxyInfoList.size()));

        // 选取速度最快的10个代理
        if (proxyInfoList.size() > 10) {
            proxyInfoList = proxyInfoList.subList(0, 10);
        }

        // 随机排序
//        Collections.shuffle(proxyInfoList);

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
