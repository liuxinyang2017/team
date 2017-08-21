package com.qatang.team.manager.core.constants;

import com.google.common.collect.Lists;
import com.qatang.team.enums.lottery.LotteryType;

import java.util.List;

/**
 * Created by yjy on 2016/12/28.
 */
public class LotteryTypeGroup {

    // 数字彩
    public static final List<LotteryType> SZC_LOTTERY_TYPE = Lists.newArrayList();

    // 预开奖彩种
    public static final List<LotteryType> PRE_DRAW_LOTTERY_TYPE = Lists.newArrayList();

    // 比赛开奖
    public static final List<LotteryType> MATCH_LOTTERY_TYPE = Lists.newArrayList();

    static {
        SZC_LOTTERY_TYPE.add(LotteryType.TC_DLT);
        SZC_LOTTERY_TYPE.add(LotteryType.FC_SSQ);
        SZC_LOTTERY_TYPE.add(LotteryType.ZC_SPF14);
        SZC_LOTTERY_TYPE.add(LotteryType.ZC_SPF9);
        SZC_LOTTERY_TYPE.add(LotteryType.ZC_BQC6);
        SZC_LOTTERY_TYPE.add(LotteryType.ZC_JQS4);

        PRE_DRAW_LOTTERY_TYPE.add(LotteryType.TC_DLT);
        PRE_DRAW_LOTTERY_TYPE.add(LotteryType.FC_SSQ);
        PRE_DRAW_LOTTERY_TYPE.add(LotteryType.ZC_SPF14);
        PRE_DRAW_LOTTERY_TYPE.add(LotteryType.ZC_SPF9);
        PRE_DRAW_LOTTERY_TYPE.add(LotteryType.ZC_BQC6);
        PRE_DRAW_LOTTERY_TYPE.add(LotteryType.ZC_JQS4);

        MATCH_LOTTERY_TYPE.add(LotteryType.JC_ZQ);
        MATCH_LOTTERY_TYPE.add(LotteryType.JC_LQ);
        MATCH_LOTTERY_TYPE.add(LotteryType.TC_BJDC);
        MATCH_LOTTERY_TYPE.add(LotteryType.TC_BJDC_SFGG);
    }

}
