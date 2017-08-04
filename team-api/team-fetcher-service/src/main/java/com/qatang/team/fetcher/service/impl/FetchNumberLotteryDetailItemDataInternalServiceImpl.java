package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.entity.FetchNumberLotteryDetailItemDataEntity;
import com.qatang.team.fetcher.exception.FetchNumberLotteryDetailItemDataException;
import com.qatang.team.fetcher.repository.FetchNumberLotteryDetailItemDataRepository;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailItemDataInternalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wp
 * @since 2017/8/4
 */
@Service
public class FetchNumberLotteryDetailItemDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements FetchNumberLotteryDetailItemDataInternalService {

    @Autowired
    private FetchNumberLotteryDetailItemDataRepository fetchNumberLotteryDetailItemDataRepository;

    protected FetchNumberLotteryDetailItemDataEntity getFetchNumberLotteryDetailItemEntityWithNullCheckForUpdate(Long fetchNumberLotteryDetailItemDataId) throws FetchNumberLotteryDetailItemDataException {
        FetchNumberLotteryDetailItemDataEntity fetchNumberLotteryDetailItemDataEntity = fetchNumberLotteryDetailItemDataRepository.findOneForUpdate(fetchNumberLotteryDetailItemDataId);
        if (fetchNumberLotteryDetailItemDataEntity == null) {
            String msg = String.format("未获取到开奖结果详情子对象抓取数据：fetchNumberLotteryDetailItemDataId=%s", fetchNumberLotteryDetailItemDataId);
            logger.error(msg);
            throw new FetchNumberLotteryDetailItemDataException(msg);
        }
        return fetchNumberLotteryDetailItemDataEntity;
    }

    @Override
    @Transactional
    public FetchNumberLotteryDetailItemData save(FetchNumberLotteryDetailItemData fetchNumberLotteryDetailItemData) throws FetchNumberLotteryDetailItemDataException {
        logger.info("新建数字彩开奖结果详情抓取数据：开始执行");

        logger.info("新建数字彩开奖结果详情抓取数据：将FetchNumberLotteryDetailItemData转换为FetchNumberLotteryDetailItemDataEntity");
        FetchNumberLotteryDetailItemDataEntity fetchNumberLotteryDetailItemDataEntity = BeanMapping.map(fetchNumberLotteryDetailItemData, FetchNumberLotteryDetailItemDataEntity.class);

        logger.info("新建数字彩开奖结果详情抓取数据：保存FetchNumberLotteryDetailItemDataEntity");
        fetchNumberLotteryDetailItemDataEntity = fetchNumberLotteryDetailItemDataRepository.save(fetchNumberLotteryDetailItemDataEntity);

        logger.info("新建数字彩开奖结果详情抓取数据：将FetchNumberLotteryDetailItemDataEntity转换为FetchNumberLotteryDetailItemData作为返回结果");
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailItemDataResult = BeanMapping.map(fetchNumberLotteryDetailItemDataEntity, FetchNumberLotteryDetailItemData.class);

        logger.info("新建数字彩开奖结果详情抓取数据：结束执行");
        return fetchNumberLotteryDetailItemDataResult;
    }

    @Override
    public FetchNumberLotteryDetailItemData get(Long id) throws FetchNumberLotteryDetailItemDataException {
        logger.info("按id获取数字彩开奖详情抓取数据子对象, id={}", id);
        FetchNumberLotteryDetailItemDataEntity fetchNumberLotteryDetailItemDataEntity = fetchNumberLotteryDetailItemDataRepository.findOne(id);
        if (fetchNumberLotteryDetailItemDataEntity == null) {
            String msg = String.format("按id获取数字彩开奖详情抓取数据子对象为空, id=%s", id);
            logger.error(msg);
            throw new FetchNumberLotteryDetailItemDataException(msg);
        }
        return BeanMapping.map(fetchNumberLotteryDetailItemDataEntity, FetchNumberLotteryDetailItemData.class);
    }

    @Override
    public List<FetchNumberLotteryDetailItemData> getByDetailId(Long detailId) throws FetchNumberLotteryDetailItemDataException {
        logger.info("根据fetchDetailId获取开奖详情抓取数据子对象信息列表，fetchDetailId={}", detailId);
        List<FetchNumberLotteryDetailItemDataEntity> fetchNumberLotteryDetailItemDataEntityList = fetchNumberLotteryDetailItemDataRepository.findByFetchDetailId(detailId);
        if (fetchNumberLotteryDetailItemDataEntityList.size() == 0) {
            String msg = String.format("根据fetchDetailId获取开奖详情抓取数据子对象信息列表为空，fetchDetailId=%s", detailId);
            logger.error(msg);
            throw new FetchNumberLotteryDetailItemDataException(msg);
        }
        return BeanMapping.mapList(fetchNumberLotteryDetailItemDataEntityList, FetchNumberLotteryDetailItemData.class);
    }

    @Override
    public ApiResponse<FetchNumberLotteryDetailItemData> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetchNumberLotteryDetailItemDataException {
        Page<FetchNumberLotteryDetailItemDataEntity> fetchNumberLotteryDetailItemDataEntityPage = fetchNumberLotteryDetailItemDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(fetchNumberLotteryDetailItemDataEntityPage, FetchNumberLotteryDetailItemData.class);
    }

    @Override
    public FetchNumberLotteryDetailItemData findByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType) throws FetchNumberLotteryDetailItemDataException {
        logger.info("根据彩种彩期抓取器获取数字彩开奖详情抓取数据子对象, lotteryType={}, phase={},抓取器={}", lotteryType.getName(), phase, fetcherType.getName());
        FetchNumberLotteryDetailItemDataEntity fetchNumberLotteryDetailItemDataEntity = fetchNumberLotteryDetailItemDataRepository.findByLotteryTypeAndPhaseAndFetcherType(lotteryType, phase, fetcherType);
        if (fetchNumberLotteryDetailItemDataEntity == null) {
            String msg = String.format("根据彩种彩期抓取器获取数字彩开奖详情抓取数据子对象为空, lotteryType=%s, phase=%s,抓取器=%s", lotteryType.getName(), phase, fetcherType.getName());
            logger.error(msg);
            throw new FetchNumberLotteryDetailItemDataException(msg);
        }
        return BeanMapping.map(fetchNumberLotteryDetailItemDataEntity, FetchNumberLotteryDetailItemData.class);
    }
}
