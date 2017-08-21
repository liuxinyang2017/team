package com.qatang.team.manager.interceptor;

import com.google.common.collect.Maps;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author jinsheng
 * @since 2016-06-20 19:19
 */
@Component
public class ModelAttributeFactory {

    private Map<String, List<?>> modelAttributeMap = Maps.newHashMap();

    @PostConstruct
    private void init() {
        modelAttributeMap.put("allLotteryTypeList", LotteryType.listAll());
        modelAttributeMap.put("lotteryTypeList", LotteryType.list());

        modelAttributeMap.put("allEnableDisableStatusList", EnableDisableStatus.listAll());
        modelAttributeMap.put("enableDisableStatusList", EnableDisableStatus.list());

        modelAttributeMap.put("allYesNoStatusList", YesNoStatus.listAll());
        modelAttributeMap.put("yesNoStatusList", YesNoStatus.list());

    }

    List<?> getModelAttributeList(String key) {
        return modelAttributeMap.get(key);
    }

    boolean containsKey(String key) {
        return modelAttributeMap.containsKey(key);
    }
}
