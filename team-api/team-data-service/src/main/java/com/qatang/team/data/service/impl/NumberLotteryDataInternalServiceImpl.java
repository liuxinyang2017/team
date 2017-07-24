package com.qatang.team.data.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.entity.NumberLotteryDataEntity;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.data.repository.NumberLotteryDataRepository;
import com.qatang.team.data.service.NumberLotteryDataInternalService;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.exception.StatusFlowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author qatang
 */
@Service
public class NumberLotteryDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements NumberLotteryDataInternalService {

    @Autowired
    private NumberLotteryDataRepository numberLotteryDataRepository;

    protected NumberLotteryDataEntity getNumberLotteryDataEntityWithNullCheckForUpdate(Long id) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findOneForUpdate(id);
        if (numberLotteryDataEntity == null) {
            throw new NumberLotteryDataException(String.format("未获取到数字彩彩果：numberLotteryDataId=%s", id));
        }
        return numberLotteryDataEntity;
    }

    protected NumberLotteryDataEntity getNumberLotteryDataEntityByLotteryTypeAndPhaseWithNullCheckForUpdate(LotteryType lotteryType, String phase) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.getByLotteryTypeAndPhase(lotteryType, phase);
        if (numberLotteryDataEntity == null) {
            throw new NumberLotteryDataException(String.format("未获取到数字彩彩果：lotteryType=%s，phase=%s", lotteryType.getName(), phase));
        }
        return numberLotteryDataEntity;
    }

    protected NumberLotteryDataEntity getCurrentNumberLotteryDataEntityWithNullCheckForUpdate(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.getByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        return numberLotteryDataEntity;
    }

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

    @Transactional
    @Override
    public NumberLotteryData update(NumberLotteryData numberLotteryData) throws NumberLotteryDataException {
        logger.info("修改数字彩数据服务：开始执行");

        Assert.notNull(numberLotteryData.getId(), "数字彩数据服务id不能为空");

        logger.info("修改数字彩数据服务：锁行查询NumberLotteryDataEntity");
        NumberLotteryDataEntity numberLotteryDataEntity = getNumberLotteryDataEntityWithNullCheckForUpdate(numberLotteryData.getId());
        copyUpdatableField(numberLotteryDataEntity, numberLotteryData);

        logger.info("修改数字彩数据服务：将NumberLotteryDataEntity转换为NumberLotteryData作为返回结果");
        NumberLotteryData numberLotteryDataResult = BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);

        logger.info("修改数字彩数据服务：结束执行");
        return numberLotteryDataResult;
    }

    @Override
    public NumberLotteryData get(Long id) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findOne(id);
        return BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    public ApiResponse<NumberLotteryData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryDataException {
        Page<NumberLotteryDataEntity> numberLotteryDataEntityPage = numberLotteryDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(numberLotteryDataEntityPage, NumberLotteryData.class);
    }

    @Override
    public NumberLotteryData getByLotteryTypeAndPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndPhase(lotteryType, phase);
        return BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    public NumberLotteryData getCurrentPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        return BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    public NumberLotteryData getPreviousPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        return getPreviousPhase(lotteryType, numberLotteryDataEntity.getPhase());
    }

    @Override
    public NumberLotteryData getPreviousPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException {
        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                .filterLessThan(QNumberLotteryData.phase, phase);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().
                paging(0, 1).
                addOrder(QNumberLotteryData.phase, PageOrderType.DESC);

        ApiResponse<NumberLotteryData> apiResponse = this.findAll(apiRequest, apiRequestPage);
        if (apiResponse.getPagedData() == null || apiResponse.getPagedData().isEmpty()) {
            return null;
        }
        return apiResponse.getPagedData().iterator().next();
    }

    @Override
    public NumberLotteryData getNextPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        return getNextPhase(lotteryType, numberLotteryDataEntity.getPhase());
    }

    @Override
    public NumberLotteryData getNextPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException {
        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                .filterGreaterThan(QNumberLotteryData.phase, phase);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().
                paging(0, 1).
                addOrder(QNumberLotteryData.phase, PageOrderType.ASC);

        ApiResponse<NumberLotteryData> apiResponse = this.findAll(apiRequest, apiRequestPage);
        if (apiResponse.getPagedData() == null || apiResponse.getPagedData().isEmpty()) {
            return null;
        }
        return apiResponse.getPagedData().iterator().next();
    }

    @Override
    @Transactional
    public NumberLotteryData updatePhaseStatus(LotteryType lotteryType, String phase, PhaseStatus toStatus, PhaseStatus checkStatus) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = getNumberLotteryDataEntityByLotteryTypeAndPhaseWithNullCheckForUpdate(lotteryType, phase);
        PhaseStatus phaseStatus = numberLotteryDataEntity.getPhaseStatus();
        if (checkStatus != null && checkStatus != phaseStatus) {
            String msg = String.format("更新状态不一致, id=%s, toStatus=%s, checkStatus=%s, currentStatus=%s", numberLotteryDataEntity.getId(), toStatus.getName(), checkStatus.getName(), phaseStatus.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        try {
            PhaseStatus.checkStatusFlow(phaseStatus, toStatus);
        } catch (StatusFlowException e) {
            String msg = String.format("更新状态失败, id=%s, fromStatus=%s, toStatus=%s", numberLotteryDataEntity.getId(), phaseStatus.getName(), toStatus.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        numberLotteryDataEntity.setPhaseStatus(toStatus);

        return BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    @Transactional
    public void switchCurrentPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = getCurrentNumberLotteryDataEntityWithNullCheckForUpdate(lotteryType);

        numberLotteryDataEntity.setIsCurrent(YesNoStatus.NO);

        NumberLotteryData nextPhase = getNextPhase(lotteryType, numberLotteryDataEntity.getPhase());

        NumberLotteryDataEntity nextLotteryPhaseEntity = numberLotteryDataRepository.findOneForUpdate(nextPhase.getId());

        nextLotteryPhaseEntity.setIsCurrent(YesNoStatus.YES);
    }

    @Override
    @Transactional
    public void specifyCurrentPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = getCurrentNumberLotteryDataEntityWithNullCheckForUpdate(lotteryType);

        if (numberLotteryDataEntity != null) {
            numberLotteryDataEntity.setIsCurrent(YesNoStatus.NO);
        }

        NumberLotteryDataEntity specifyNumberLotteryDataEntity = getNumberLotteryDataEntityByLotteryTypeAndPhaseWithNullCheckForUpdate(lotteryType, phase);

        specifyNumberLotteryDataEntity.setIsCurrent(YesNoStatus.YES);
    }
}
