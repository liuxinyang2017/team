package com.qatang.team.data.service.impl;

import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.entity.NumberLotteryDataEntity;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.data.repository.NumberLotteryDataRepository;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.data.service.NumberLotteryDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qatang
 */
@Service
public class NumberLotteryDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements NumberLotteryDataInternalService {

    @Autowired
    private NumberLotteryDataRepository numberLotteryDataRepository;

    @Override
    public NumberLotteryData save(NumberLotteryData numberLotteryData) throws NumberLotteryDataException {
        logger.info("新建数字彩数据：开始执行");

        logger.info("新建数字彩数据：将NumberLotteryData转换为NumberLotteryDataEntity");
        NumberLotteryDataEntity numberLotteryDataEntity = BeanMapping.map(numberLotteryData, NumberLotteryDataEntity.class);

        logger.info("新建数字彩数据：保存NumberLotteryDataEntity");
        numberLotteryDataEntity = numberLotteryDataRepository.save(numberLotteryDataEntity);

        logger.info("新建数字彩数据：将NumberLotteryDataEntity转换为NumberLotteryData作为返回结果");
        NumberLotteryData numberLotteryDataResult = BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);

        logger.info("新建数字彩数据：结束执行");
        return numberLotteryDataResult;
    }
}
