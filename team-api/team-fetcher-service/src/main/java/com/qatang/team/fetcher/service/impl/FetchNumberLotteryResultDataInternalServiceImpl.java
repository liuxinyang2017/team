package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.entity.FetchNumberLotteryResultDataEntity;
import com.qatang.team.fetcher.exception.FetchNumberLotteryResultDataException;
import com.qatang.team.fetcher.repository.FetchNumberLotteryResultDataRepository;
import com.qatang.team.fetcher.service.FetchNumberLotteryResultDataInternalService;
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
public class FetchNumberLotteryResultDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements FetchNumberLotteryResultDataInternalService {

    @Autowired
    private FetchNumberLotteryResultDataRepository fetchNumberLotteryResultDataRepository;

    protected FetchNumberLotteryResultDataEntity getNumberLotteryFetchResultDataEntityWithNullCheckForUpdate(Long numberLotteryFetchResultDataId) throws FetchNumberLotteryResultDataException {
        FetchNumberLotteryResultDataEntity fetchNumberLotteryResultDataEntity = fetchNumberLotteryResultDataRepository.findOneForUpdate(numberLotteryFetchResultDataId);
        if (fetchNumberLotteryResultDataEntity == null) {
            String msg = String.format("未获取到开奖结果抓取数据：numberLotteryFetchResultDataId=%s", numberLotteryFetchResultDataId);
            logger.error(msg);
            throw new FetchNumberLotteryResultDataException(msg);
        }
        return fetchNumberLotteryResultDataEntity;
    }

    @Override
    @Transactional
    public FetchNumberLotteryResultData save(FetchNumberLotteryResultData fetchNumberLotteryResultData) throws FetchNumberLotteryResultDataException {
        logger.info("新建数字彩开奖结果抓取数据：开始执行");

        logger.info("新建数字彩开奖结果抓取数据：将NumberLotteryFetchResultData转换为NumberLotteryFetchResultDataEntity");
        FetchNumberLotteryResultDataEntity fetchNumberLotteryResultDataEntity = BeanMapping.map(fetchNumberLotteryResultData, FetchNumberLotteryResultDataEntity.class);

        logger.info("新建数字彩开奖结果抓取数据：保存NumberLotteryFetchResultDataEntity");
        fetchNumberLotteryResultDataEntity = fetchNumberLotteryResultDataRepository.save(fetchNumberLotteryResultDataEntity);

        logger.info("新建数字彩开奖结果抓取数据：将NumberLotteryFetchResultDataEntity转换为NumberLotteryFetchResultData作为返回结果");
        FetchNumberLotteryResultData fetchResultDataNumberLotteryResult = BeanMapping.map(fetchNumberLotteryResultDataEntity, FetchNumberLotteryResultData.class);

        logger.info("新建数字彩开奖结果抓取数据：结束执行");
        return fetchResultDataNumberLotteryResult;
    }

    @Override
    public ApiResponse<FetchNumberLotteryResultData> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetchNumberLotteryResultDataException {
        Page<FetchNumberLotteryResultDataEntity> numberLotteryFetchResultDataEntityPage = fetchNumberLotteryResultDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(numberLotteryFetchResultDataEntityPage, FetchNumberLotteryResultData.class);
    }

    @Override
    public FetchNumberLotteryResultData get(Long id) throws FetchNumberLotteryResultDataException {
        logger.info("按id获取数字彩开奖结果抓取数据, numberLotteryFetchResultDataId={}", id);
        FetchNumberLotteryResultDataEntity fetchNumberLotteryResultDataEntity = fetchNumberLotteryResultDataRepository.findOne(id);
        if (fetchNumberLotteryResultDataEntity == null) {
            String msg = String.format("按id获取数字彩开奖结果抓取数据为空, numberLotteryFetchResultDataId=%s", id);
            logger.error(msg);
            throw new FetchNumberLotteryResultDataException(msg);
        }
        return BeanMapping.map(fetchNumberLotteryResultDataEntity, FetchNumberLotteryResultData.class);
    }

    @Override
    @Transactional
    public FetchNumberLotteryResultData update(FetchNumberLotteryResultData fetchNumberLotteryResultData) throws FetchNumberLotteryResultDataException {
        logger.info("修改数字彩开奖结果抓取数据：开始执行");

        Assert.notNull(fetchNumberLotteryResultData.getId(), "抓取日志编号不能为空");

        logger.info("修改数字彩开奖结果抓取数据：锁行查询NumberLotteryFetchResultDataEntity");
        FetchNumberLotteryResultDataEntity fetchNumberLotteryResultDataEntity = fetchNumberLotteryResultDataRepository.findOne(fetchNumberLotteryResultData.getId());

        fetchNumberLotteryResultDataRepository.detach(fetchNumberLotteryResultDataEntity);

        fetchNumberLotteryResultDataEntity = this.getNumberLotteryFetchResultDataEntityWithNullCheckForUpdate(fetchNumberLotteryResultData.getId());

        copyUpdatableField(fetchNumberLotteryResultDataEntity, fetchNumberLotteryResultData);

        logger.info("修改数字彩开奖结果抓取数据：将NumberLotteryFetchResultDataEntity转换为NumberLotteryFetchResultData作为返回结果");
        FetchNumberLotteryResultData fetchResultDataNumberLotteryResult = BeanMapping.map(fetchNumberLotteryResultDataEntity, FetchNumberLotteryResultData.class);

        logger.info("修改数字彩开奖结果抓取数据：结束执行");
        return fetchResultDataNumberLotteryResult;
    }

    @Override
    public FetchNumberLotteryResultData getByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType) {
        FetchNumberLotteryResultDataEntity fetchNumberLotteryResultDataEntity = fetchNumberLotteryResultDataRepository.findByLotteryTypeAndPhaseAndFetcherType(lotteryType, phase, fetcherType);
        if (fetchNumberLotteryResultDataEntity == null) {
            String msg = String.format("根据彩种[%s],彩期[%s],抓取来源数据类型[%s]:获取开奖结果抓取数据为空", lotteryType.getName(), phase, fetcherType.getName());
            logger.error(msg);
            throw new FetchNumberLotteryResultDataException(msg);
        }
        return BeanMapping.map(fetchNumberLotteryResultDataEntity, FetchNumberLotteryResultData.class);
    }
}
