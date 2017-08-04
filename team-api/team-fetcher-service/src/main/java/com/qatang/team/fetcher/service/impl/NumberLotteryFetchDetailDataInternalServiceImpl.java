package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.entity.NumberLotteryFetchDetailDataEntity;
import com.qatang.team.fetcher.exception.NumberLotteryFetchDetailDataException;
import com.qatang.team.fetcher.repository.NumberLotteryFetchDetailDataRepository;
import com.qatang.team.fetcher.service.NumberLotteryFetchDetailDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * 数字彩开奖详情抓取数据服务实现
 * @author wp
 * @since 2017/7/23
 */
@Service
public class NumberLotteryFetchDetailDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements NumberLotteryFetchDetailDataInternalService {

    @Autowired
    private NumberLotteryFetchDetailDataRepository numberLotteryFetchDetailDataRepository;

    protected NumberLotteryFetchDetailDataEntity getNumberLotteryFetchDetailDataEntityWithNullCheckForUpdate(Long numberLotteryFetchDetailDataId) throws NumberLotteryFetchDetailDataException {
        NumberLotteryFetchDetailDataEntity numberLotteryFetchDetailDataEntity = numberLotteryFetchDetailDataRepository.findOneForUpdate(numberLotteryFetchDetailDataId);
        if (numberLotteryFetchDetailDataEntity == null) {
            String msg = String.format("未获取到开奖详情抓取数据：numberLotteryFetchDetailDataId=%s", numberLotteryFetchDetailDataId);
            logger.error(msg);
            throw new NumberLotteryFetchDetailDataException(msg);
        }
        return numberLotteryFetchDetailDataEntity;
    }

    @Override
    @Transactional
    public FetchNumberLotteryDetailItemData save(FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData) throws NumberLotteryFetchDetailDataException {
        logger.info("新建数字彩开奖详情抓取数据：开始执行");

        logger.info("新建数字彩开奖详情抓取数据：将NumberLotteryFetchDetailData转换为NumberLotteryFetchDetailDataEntity");
        NumberLotteryFetchDetailDataEntity numberLotteryFetchDetailDataEntity = BeanMapping.map(fetchNumberLotteryDetailData,NumberLotteryFetchDetailDataEntity.class);

        logger.info("新建数字彩开奖详情抓取数据：保存NumberLotteryFetchDetailDataEntity");
        numberLotteryFetchDetailDataEntity = numberLotteryFetchDetailDataRepository.save(numberLotteryFetchDetailDataEntity);

        logger.info("新建数字彩开奖详情抓取数据：将NumberLotteryFetchDetailDataEntity转换为NumberLotteryFetchDetailData作为返回结果");
        FetchNumberLotteryDetailItemData fetcherLotteryFetcheResult = BeanMapping.map(numberLotteryFetchDetailDataEntity, FetchNumberLotteryDetailItemData.class);

        logger.info("新建数字彩开奖详情抓取数据：结束执行");
        return fetcherLotteryFetcheResult;
    }

    @Override
    public ApiResponse<FetchNumberLotteryDetailItemData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryFetchDetailDataException {
        Page<NumberLotteryFetchDetailDataEntity> numberLotteryFetchDetailDataEntityPage = numberLotteryFetchDetailDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(numberLotteryFetchDetailDataEntityPage, FetchNumberLotteryDetailItemData.class);
    }

    @Override
    public FetchNumberLotteryDetailItemData get(Long id) throws NumberLotteryFetchDetailDataException {
        logger.info("按id获取数字彩开奖详情抓取数据, numberLotteryFetchDetailDataId={}", id);
        NumberLotteryFetchDetailDataEntity numberLotteryFetchDetailDataEntity = numberLotteryFetchDetailDataRepository.findOne(id);
        if (numberLotteryFetchDetailDataEntity == null) {
            String msg = String.format("按id获取数字彩开奖详情抓取数据, numberLotteryFetchDetailDataId=%s", id);
            logger.error(msg);
            throw new NumberLotteryFetchDetailDataException(msg);
        }
        return BeanMapping.map(numberLotteryFetchDetailDataEntity, FetchNumberLotteryDetailItemData.class);
    }

    @Override
    @Transactional
    public FetchNumberLotteryDetailItemData update(FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData) throws NumberLotteryFetchDetailDataException {
        logger.info("修改数字彩开奖详情抓取数据：开始执行");

        Assert.notNull(fetchNumberLotteryDetailData.getId(), "抓取日志编号不能为空");

        logger.info("修改数字彩开奖详情抓取数据：锁行查询NumberLotteryFetchDetailDataEntity");
        NumberLotteryFetchDetailDataEntity numberLotteryFetchDetailDataEntity = numberLotteryFetchDetailDataRepository.findOne(fetchNumberLotteryDetailData.getId());

        numberLotteryFetchDetailDataRepository.detach(numberLotteryFetchDetailDataEntity);

        numberLotteryFetchDetailDataEntity = getNumberLotteryFetchDetailDataEntityWithNullCheckForUpdate(fetchNumberLotteryDetailData.getId());
        copyUpdatableField(numberLotteryFetchDetailDataEntity, fetchNumberLotteryDetailData);

        logger.info("修改数字彩开奖详情抓取数据：将NumberLotteryFetchDetailDataEntity转换为NumberLotteryFetchDetailData作为返回结果");
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailDataResult = BeanMapping.map(numberLotteryFetchDetailDataEntity, FetchNumberLotteryDetailItemData.class);

        logger.info("修改数字彩开奖详情抓取数据：结束执行");
        return fetchNumberLotteryDetailDataResult;
    }

    @Override
    public FetchNumberLotteryDetailItemData getByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType) {
        NumberLotteryFetchDetailDataEntity numberLotteryFetchDetailDataEntity = numberLotteryFetchDetailDataRepository.findByLotteryTypeAndPhaseAndFetcherType(lotteryType, phase, fetcherType);
        if (numberLotteryFetchDetailDataEntity == null) {
            String msg = String.format("根据彩种[%s],彩期[%s],抓取器[%s]获取开奖详情抓取数据为空", lotteryType.getName(), phase, fetcherType.getName());
            logger.error(msg);
            throw new NumberLotteryFetchDetailDataException(msg);
        }
        return BeanMapping.map(numberLotteryFetchDetailDataEntity, FetchNumberLotteryDetailItemData.class);
    }
}
