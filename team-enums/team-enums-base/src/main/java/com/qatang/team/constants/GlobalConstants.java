package com.qatang.team.constants;

import com.google.common.collect.Lists;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.enums.lottery.LotteryType;

import java.util.List;

/**
 * 全局常量
 * @author qatang
 */
public class GlobalConstants {

    /**
     * 代理测试通过初始化得分
     */
    public final static int PROXY_INIT_SCORE = 10;

    /**
     * 代理连续测试失败次数，超过之后将被置为已作废
     */
    public final static int PROXY_FAILED_TEST_COUNT = 3;

    /**
     * 抓取结果开始对比的最小数量
     */
    public final static int FETCH_DATA_MIN_COUNT = 1;

    /**
     * 正在使用的代理抓取器类型
     */
    public final static List<ProxyFetcherType> PROXY_FETCHER_TYPE_LIST = Lists.newArrayList(
//            ProxyFetcherType.P_CZ88,
//            ProxyFetcherType.P_XICI,
//            ProxyFetcherType.P_KUAI,
            ProxyFetcherType.P_MIMVP
    );

    /**
     * 正在使用的代理检测器类型
     */
    public final static List<ProxyValidatorType> PROXY_VALIDATOR_TYPE_LIST = Lists.newArrayList(
            ProxyValidatorType.V_BAIDU,
            ProxyValidatorType.V_FUCAI
    );

    /**
     * 正在使用的代理检测器类型
     */
    public final static List<FetcherType> FETCHER_TYPE_LIST = Lists.newArrayList(
            FetcherType.F_OFFICIAL
    );

    /**
     * 目前支持的数字彩彩种列表
     */
    public final static List<LotteryType> NUMBER_LOTTERY_LIST = Lists.newArrayList(
            LotteryType.FC_SSQ
    );
}
