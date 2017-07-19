package com.qatang.team.scheduler.constant;

import com.google.common.collect.Lists;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.enums.fetcher.ProxyValidatorType;

import java.util.List;

/**
 * @author qatang
 */
public class Constants {
    /**
     * 正在试用的代理抓取器类型
     */
    public final static List<ProxyFetcherType> proxyFetcherTypeList = Lists.newArrayList(
            ProxyFetcherType.P_XICI,
            ProxyFetcherType.P_KUAI,
            ProxyFetcherType.P_CZ88
    );

    /**
     * 正在试用的代理检测器类型
     */
    public final static List<ProxyValidatorType> proxyValidatorTypeList = Lists.newArrayList(
            ProxyValidatorType.V_BAIDU,
            ProxyValidatorType.V_FUCAI
    );
}
