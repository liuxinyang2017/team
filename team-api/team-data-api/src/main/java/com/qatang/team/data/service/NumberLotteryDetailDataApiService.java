package com.qatang.team.data.service;

import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.exception.NumberLotteryDetailDataException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 数字彩详情服务
 * @author qatang
 */
@FeignClient("team-data-service")
@RequestMapping("/data/numberLotteryDetailData")
public interface NumberLotteryDetailDataApiService {

    /**
     * 批量创建
     * @param numberLotteryDetailDataList 数字彩详情
     * @return 数字彩详情
     * @throws NumberLotteryDetailDataException
     */
    List<NumberLotteryDetailData> batchSave(List<NumberLotteryDetailData> numberLotteryDetailDataList) throws NumberLotteryDetailDataException;
}
