package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.entity.FetchNumberLotteryDetailDataEntity;
import com.qatang.team.fetcher.entity.FetchNumberLotteryDetailItemDataEntity;
import com.qatang.team.fetcher.exception.FetchNumberLotteryDetailDataException;
import com.qatang.team.fetcher.repository.FetchNumberLotteryDetailDataRepository;
import com.qatang.team.fetcher.repository.FetchNumberLotteryDetailItemDataRepository;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 数字彩开奖详情抓取数据服务实现
 * @author wp
 * @since 2017/7/23
 */
@Service
public class FetchNumberLotteryDetailDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements FetchNumberLotteryDetailDataInternalService {

    @Autowired
    private FetchNumberLotteryDetailDataRepository fetchNumberLotteryDetailDataRepository;

    @Autowired
    private FetchNumberLotteryDetailItemDataRepository fetchNumberLotteryDetailItemDataRepository;

    protected FetchNumberLotteryDetailDataEntity getNumberLotteryFetchDetailDataEntityWithNullCheckForUpdate(Long numberLotteryFetchDetailDataId) throws FetchNumberLotteryDetailDataException {
        FetchNumberLotteryDetailDataEntity fetchNumberLotteryDetailDataEntity = fetchNumberLotteryDetailDataRepository.findOneForUpdate(numberLotteryFetchDetailDataId);
        if (fetchNumberLotteryDetailDataEntity == null) {
            String msg = String.format("未获取到开奖详情抓取数据：numberLotteryFetchDetailDataId=%s", numberLotteryFetchDetailDataId);
            logger.error(msg);
            throw new FetchNumberLotteryDetailDataException(msg);
        }
        return fetchNumberLotteryDetailDataEntity;
    }

    @Override
    @Transactional
    public FetchNumberLotteryDetailData save(FetchNumberLotteryDetailData fetchNumberLotteryDetailData, List<FetchNumberLotteryDetailItemData> fetchNumberLotteryDetailItemDataList) throws FetchNumberLotteryDetailDataException {
        logger.info("新建数字彩开奖详情抓取数据：开始执行");

        logger.info("新建数字彩开奖详情抓取数据：将FetchNumberLotteryDetailData转换为NumberLotteryFetchDetailDataEntity");
        FetchNumberLotteryDetailDataEntity fetchNumberLotteryDetailDataEntity = BeanMapping.map(fetchNumberLotteryDetailData,FetchNumberLotteryDetailDataEntity.class);

        logger.info("新建数字彩开奖详情抓取数据：保存NumberLotteryFetchDetailDataEntity");
        fetchNumberLotteryDetailDataEntity = fetchNumberLotteryDetailDataRepository.save(fetchNumberLotteryDetailDataEntity);

        logger.info("新建数字彩开奖详情抓取数据: 将FetchNumberLotteryDetailItemData转换为FetchNumberLotteryDetailItemDataEntity");
        List<FetchNumberLotteryDetailItemDataEntity> fetchNumberLotteryDetailItemDataEntityList = BeanMapping.mapList(fetchNumberLotteryDetailItemDataList, FetchNumberLotteryDetailItemDataEntity.class);

        logger.info("新建数字彩开奖详情抓取数据：保存FetchNumberLotteryDetailItemDataEntity");
        fetchNumberLotteryDetailItemDataRepository.save(fetchNumberLotteryDetailItemDataEntityList);

        logger.info("新建数字彩开奖详情抓取数据：将NumberLotteryFetchDetailDataEntity转换为NumberLotteryFetchDetailData作为返回结果");
        FetchNumberLotteryDetailData fetchNumberLotteryDetailDataResult = BeanMapping.map(fetchNumberLotteryDetailDataEntity, FetchNumberLotteryDetailData.class);

        logger.info("新建数字彩开奖详情抓取数据：结束执行");
        return fetchNumberLotteryDetailDataResult;
    }

    @Override
    public ApiResponse<FetchNumberLotteryDetailData> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetchNumberLotteryDetailDataException {
        Page<FetchNumberLotteryDetailDataEntity> fetchNumberLotteryDetailDataEntityPage = fetchNumberLotteryDetailDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(fetchNumberLotteryDetailDataEntityPage, FetchNumberLotteryDetailData.class);
    }

    @Override
    public FetchNumberLotteryDetailData get(Long id) throws FetchNumberLotteryDetailDataException {
        logger.info("按id获取数字彩开奖详情抓取数据, fetchNumberLotteryFetchDetailDataId={}", id);
        FetchNumberLotteryDetailDataEntity fetchNumberLotteryDetailDataEntity = fetchNumberLotteryDetailDataRepository.findOne(id);
        if (fetchNumberLotteryDetailDataEntity == null) {
            String msg = String.format("按id获取数字彩开奖详情抓取数据, fetchNumberLotteryFetchDetailDataId=%s", id);
            logger.error(msg);
            throw new FetchNumberLotteryDetailDataException(msg);
        }
        return BeanMapping.map(fetchNumberLotteryDetailDataEntity, FetchNumberLotteryDetailData.class);
    }

    @Override
    @Transactional
    public FetchNumberLotteryDetailData update(FetchNumberLotteryDetailData fetchNumberLotteryDetailData) throws FetchNumberLotteryDetailDataException {
        logger.info("修改数字彩开奖详情抓取数据：开始执行");

        Assert.notNull(fetchNumberLotteryDetailData.getId(), "抓取日志编号不能为空");

        logger.info("修改数字彩开奖详情抓取数据：锁行查询NumberLotteryFetchDetailDataEntity");
        FetchNumberLotteryDetailDataEntity fetchNumberLotteryDetailDataEntity = fetchNumberLotteryDetailDataRepository.findOne(fetchNumberLotteryDetailData.getId());

        fetchNumberLotteryDetailDataRepository.detach(fetchNumberLotteryDetailDataEntity);

        fetchNumberLotteryDetailDataEntity = getNumberLotteryFetchDetailDataEntityWithNullCheckForUpdate(fetchNumberLotteryDetailData.getId());
        copyUpdatableField(fetchNumberLotteryDetailDataEntity, fetchNumberLotteryDetailData);

        logger.info("修改数字彩开奖详情抓取数据：将NumberLotteryFetchDetailDataEntity转换为NumberLotteryFetchDetailData作为返回结果");
        FetchNumberLotteryDetailData fetchNumberLotteryDetailDataResult = BeanMapping.map(fetchNumberLotteryDetailDataEntity, FetchNumberLotteryDetailData.class);

        logger.info("修改数字彩开奖详情抓取数据：结束执行");
        return fetchNumberLotteryDetailDataResult;
    }
}
