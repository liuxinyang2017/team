package com.qatang.team.data.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.entity.NumberLotteryDetailDataEntity;
import com.qatang.team.data.exception.NumberLotteryDetailDataException;
import com.qatang.team.data.repository.NumberLotteryDetailDataRepository;
import com.qatang.team.data.service.NumberLotteryDetailDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author qatang
 */
@Service
public class NumberLotteryDetailDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements NumberLotteryDetailDataInternalService {

    @Autowired
    private NumberLotteryDetailDataRepository numberLotteryDetailDataRepository;

    protected NumberLotteryDetailDataEntity getNumberLotteryDetailDataEntityWithNullCheckForUpdate(Long id) throws NumberLotteryDetailDataException {
        logger.info("查询数字彩开奖详情，id={}", id);
        NumberLotteryDetailDataEntity numberLotteryDetailDataEntity = numberLotteryDetailDataRepository.findOne(id);

        logger.info("解除hibernate entityManager托管，id={}", id);
        numberLotteryDetailDataRepository.detach(numberLotteryDetailDataEntity);

        logger.info("锁行查询数次彩详情，id={}", id);
        numberLotteryDetailDataEntity = numberLotteryDetailDataRepository.findOneForUpdate(id);

        if (numberLotteryDetailDataEntity == null) {
            logger.error("锁行查询数次彩详情为空，id={}", id);
            throw new NumberLotteryDetailDataException(String.format("锁行查询数次彩详情为空，id=%s", id));
        }

        return numberLotteryDetailDataEntity;
    }

    protected NumberLotteryDetailDataEntity getNumberLotteryDetailDataEntityWithNullCheck(Long id) throws NumberLotteryDetailDataException {
        logger.info("查询数字彩开奖详情，id={}", id);
        NumberLotteryDetailDataEntity numberLotteryDetailDataEntity = numberLotteryDetailDataRepository.findOne(id);

        if (numberLotteryDetailDataEntity == null) {
            logger.error("查询数次彩详情为空，id={}", id);
            throw new NumberLotteryDetailDataException(String.format("查询数次彩详情为空，id=%s", id));
        }

        return numberLotteryDetailDataEntity;
    }

    @Override
    public List<NumberLotteryDetailData> batchSave(List<NumberLotteryDetailData> numberLotteryDetailDataList) throws NumberLotteryDetailDataException {
        logger.info("批量新建数字彩开奖详情数据：开始执行");

        logger.info("批量新建数字彩开奖详情数据：将NumberLotteryDetailDataList转换为NumberLotteryDetailDataEntityList");
        List<NumberLotteryDetailDataEntity> numberLotteryDetailDataEntityList = BeanMapping.mapList(numberLotteryDetailDataList, NumberLotteryDetailDataEntity.class);

        logger.info("批量新建数字彩开奖详情数据：保存NumberLotteryDetailDataEntityList");
        numberLotteryDetailDataEntityList = (List<NumberLotteryDetailDataEntity>) numberLotteryDetailDataRepository.save(numberLotteryDetailDataEntityList);

        logger.info("新建数字彩开奖详情数据：将NumberLotteryDetailDataEntityList转换为NumberLotteryDetailDataList作为返回结果");
        List<NumberLotteryDetailData> numberLotteryDetailDataListResult = BeanMapping.mapList(numberLotteryDetailDataEntityList, NumberLotteryDetailData.class);

        logger.info("批量新建数字彩开奖详情数据：结束执行");
        return numberLotteryDetailDataListResult;
    }

    @Transactional
    @Override
    public NumberLotteryDetailData update(NumberLotteryDetailData numberLotteryDetailData) throws NumberLotteryDetailDataException {
        logger.info("修改数字彩开奖详情数据：开始执行");
        NumberLotteryDetailDataEntity numberLotteryDetailDataEntity = this.getNumberLotteryDetailDataEntityWithNullCheckForUpdate(numberLotteryDetailData.getId());

        logger.info("修改数字彩开奖详情数据: 拷贝可修改属性的值");
        copyUpdatableField(numberLotteryDetailDataEntity, numberLotteryDetailData);

        logger.info("修改数字彩开奖详情数据：将NumberLotteryDetailDataEntity转换为NumberLotteryDetailData作为返回结果");
        NumberLotteryDetailData numberLotteryDetailDataResult = BeanMapping.map(numberLotteryDetailDataEntity, NumberLotteryDetailData.class);

        logger.info("修改数字彩开奖详情数据：结束执行");
        return numberLotteryDetailDataResult;
    }

    @Override
    public NumberLotteryDetailData get(Long id) throws NumberLotteryDetailDataException {
        NumberLotteryDetailDataEntity numberLotteryDetailDataEntity = this.getNumberLotteryDetailDataEntityWithNullCheck(id);
        return BeanMapping.map(numberLotteryDetailDataEntity, NumberLotteryDetailData.class);
    }

    @Override
    public ApiResponse<NumberLotteryDetailData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryDetailDataException {
        Page<NumberLotteryDetailDataEntity> numberLotteryDetailDataEntityPage = numberLotteryDetailDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(numberLotteryDetailDataEntityPage, NumberLotteryDetailData.class);
    }
}
