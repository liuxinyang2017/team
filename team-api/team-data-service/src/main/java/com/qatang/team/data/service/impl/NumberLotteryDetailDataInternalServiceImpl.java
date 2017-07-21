package com.qatang.team.data.service.impl;

import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.entity.NumberLotteryDetailDataEntity;
import com.qatang.team.data.exception.NumberLotteryDetailDataException;
import com.qatang.team.data.repository.NumberLotteryDetailDataRepository;
import com.qatang.team.data.service.NumberLotteryDetailDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qatang
 */
@Service
public class NumberLotteryDetailDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements NumberLotteryDetailDataInternalService {

    @Autowired
    private NumberLotteryDetailDataRepository numberLotteryDetailDataRepository;

    @Override
    public NumberLotteryDetailData save(NumberLotteryDetailData numberLotteryDetailData) throws NumberLotteryDetailDataException {
        logger.info("新建数字彩开奖详情数据：开始执行");

        logger.info("新建数字彩开奖详情数据：将NumberLotteryDetailData转换为NumberLotteryDetailDataEntity");
        NumberLotteryDetailDataEntity numberLotteryDetailDataEntity = BeanMapping.map(numberLotteryDetailData, NumberLotteryDetailDataEntity.class);

        logger.info("新建数字彩开奖详情数据：保存NumberLotteryDetailDataEntity");
        numberLotteryDetailDataEntity = numberLotteryDetailDataRepository.save(numberLotteryDetailDataEntity);

        logger.info("新建数字彩开奖详情数据：将NumberLotteryDetailDataEntity转换为NumberLotteryDetailData作为返回结果");
        NumberLotteryDetailData numberLotteryDetailDataResult = BeanMapping.map(numberLotteryDetailDataEntity, NumberLotteryDetailData.class);

        logger.info("新建数字彩开奖详情数据：结束执行");
        return numberLotteryDetailDataResult;
    }
}
