package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.entity.NumberLotteryFetchResultDataEntity;
import com.qatang.team.fetcher.exception.NumberLotteryFetchResultDataException;
import com.qatang.team.fetcher.repository.NumberLotteryFetchResultDataRepository;
import com.qatang.team.fetcher.service.NumberLotteryFetchResultDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author wp
 * @since 2017/7/23
 */
@Service
public class NumberLotteryFetchResultDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements NumberLotteryFetchResultDataInternalService {

    @Autowired
    private NumberLotteryFetchResultDataRepository numberLotteryFetchResultDataRepository;

    protected NumberLotteryFetchResultDataEntity getNumberLotteryFetchResultDataEntityWithNullCheckForUpdate(Long numberLotteryFetchResultDataId) throws NumberLotteryFetchResultDataException {
        return numberLotteryFetchResultDataRepository.findOneForUpdate(numberLotteryFetchResultDataId);
    }

    @Override
    @Transactional
    public NumberLotteryFetchResultData save(NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException {
        logger.info("新建数字彩开奖结果抓取数据：开始执行");

        logger.info("新建数字彩开奖结果抓取数据：将NumberLotteryFetchResultData转换为NumberLotteryFetchResultDataEntity");
        NumberLotteryFetchResultDataEntity numberLotteryFetchResultDataEntity = BeanMapping.map(numberLotteryFetchResultData, NumberLotteryFetchResultDataEntity.class);

        logger.info("新建数字彩开奖结果抓取数据：保存NumberLotteryFetchResultDataEntity");
        numberLotteryFetchResultDataEntity = numberLotteryFetchResultDataRepository.save(numberLotteryFetchResultDataEntity);

        logger.info("新建数字彩开奖结果抓取数据：将NumberLotteryFetchResultDataEntity转换为NumberLotteryFetchResultData作为返回结果");
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = BeanMapping.map(numberLotteryFetchResultDataEntity, NumberLotteryFetchResultData.class);

        logger.info("新建数字彩开奖结果抓取数据：结束执行");
        return numberLotteryFetchResultDataResult;
    }

    @Override
    public ApiResponse<NumberLotteryFetchResultData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryFetchResultDataException {
        Page<NumberLotteryFetchResultDataEntity> numberLotteryFetchResultDataEntityPage = numberLotteryFetchResultDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(numberLotteryFetchResultDataEntityPage, NumberLotteryFetchResultData.class);
    }

    @Override
    public NumberLotteryFetchResultData get(Long id) throws NumberLotteryFetchResultDataException {
        logger.info("按id获取数字彩开奖结果抓取数据, numberLotteryFetchResultDataId={}", id);
        NumberLotteryFetchResultDataEntity numberLotteryFetchResultDataEntity = numberLotteryFetchResultDataRepository.findOne(id);
        if (numberLotteryFetchResultDataEntity == null) {
            throw new NumberLotteryFetchResultDataException(String.format("按id获取数字彩开奖结果抓取数据, numberLotteryFetchResultDataId=%s", id));
        }
        return BeanMapping.map(numberLotteryFetchResultDataEntity, NumberLotteryFetchResultData.class);
    }

    @Override
    @Transactional
    public NumberLotteryFetchResultData update(NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException {
        logger.info("修改数字彩开奖结果抓取数据：开始执行");

        Assert.notNull(numberLotteryFetchResultData.getId(), "抓取日志编号不能为空");

        logger.info("修改数字彩开奖结果抓取数据：锁行查询NumberLotteryFetchResultDataEntity");
        NumberLotteryFetchResultDataEntity numberLotteryFetchResultDataEntity = getNumberLotteryFetchResultDataEntityWithNullCheckForUpdate(numberLotteryFetchResultData.getId());
        copyUpdatableField(numberLotteryFetchResultDataEntity, numberLotteryFetchResultData);

        logger.info("修改数字彩开奖结果抓取数据：将NumberLotteryFetchResultDataEntity转换为NumberLotteryFetchResultData作为返回结果");
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = BeanMapping.map(numberLotteryFetchResultDataEntity, NumberLotteryFetchResultData.class);

        logger.info("修改数字彩开奖结果抓取数据：结束执行");
        return numberLotteryFetchResultDataResult;
    }
}