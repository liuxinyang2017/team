package com.qatang.team.data.service.impl;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.core.util.CoreDateUtils;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findOne(numberLotteryData.getId());

        numberLotteryDataRepository.detach(numberLotteryDataEntity);

        numberLotteryDataEntity = this.getNumberLotteryDataEntityWithNullCheckForUpdate(numberLotteryData.getId());
        copyUpdatableField(numberLotteryDataEntity, numberLotteryData);

        logger.info("修改数字彩数据服务：将NumberLotteryDataEntity转换为NumberLotteryData作为返回结果");
        NumberLotteryData numberLotteryDataResult = BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);

        logger.info("修改数字彩数据服务：结束执行");
        return numberLotteryDataResult;
    }

    @Override
    public NumberLotteryData get(Long id) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findOne(id);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("根据主键[%s]获取数字彩彩果为空", id);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
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
        if (numberLotteryDataEntity == null) {
            String msg = String.format("根据彩种[%s]、彩期[%s]获取数字彩彩果为空", lotteryType.getName(), phase);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        return BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    public NumberLotteryData getCurrentPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("根据彩种[%s]获取当前期为空", lotteryType.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        return BeanMapping.map(numberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    public NumberLotteryData getPreviousPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("根据彩种[%s]获取当前期的上一期，获取当前期为空", lotteryType.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
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
            String msg = String.format("获取彩种[%s]、彩期[%s]的上一期为空", lotteryType.getName(), phase);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        return apiResponse.getPagedData().iterator().next();
    }

    @Override
    public NumberLotteryData getNextPhase(LotteryType lotteryType) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("根据彩种[%s]获取当前期的下一期，获取当前期为空", lotteryType.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
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
            String msg = String.format("获取彩种[%s]、彩期[%s]的下一期为空", lotteryType.getName(), phase);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        return apiResponse.getPagedData().iterator().next();
    }

    @Override
    @Transactional
    public NumberLotteryData updateStatus(LotteryType lotteryType, String phase, PhaseStatus toStatus, PhaseStatus checkStatus) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndPhase(lotteryType, phase);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("更新彩期状态，根据彩种[%s]、彩期[%s]获取数字彩彩果为空", lotteryType.getName(), phase);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }

        numberLotteryDataRepository.detach(numberLotteryDataEntity);

        numberLotteryDataEntity = this.getNumberLotteryDataEntityWithNullCheckForUpdate(numberLotteryDataEntity.getId());
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
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("切换当前期，根据彩种[%s]获取当前期为空", lotteryType.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }

        numberLotteryDataRepository.detach(numberLotteryDataEntity);

        numberLotteryDataEntity = this.getNumberLotteryDataEntityWithNullCheckForUpdate(numberLotteryDataEntity.getId());
        numberLotteryDataEntity.setIsCurrent(YesNoStatus.NO);

        NumberLotteryData nextPhase = getNextPhase(lotteryType, numberLotteryDataEntity.getPhase());

        NumberLotteryDataEntity nextLotteryPhaseEntity = numberLotteryDataRepository.findOneForUpdate(nextPhase.getId());

        nextLotteryPhaseEntity.setIsCurrent(YesNoStatus.YES);
    }

    @Override
    @Transactional
    public NumberLotteryData specifyCurrentPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndIsCurrent(lotteryType, YesNoStatus.YES);
        if (numberLotteryDataEntity != null) {
            numberLotteryDataRepository.detach(numberLotteryDataEntity);

            numberLotteryDataEntity = this.getNumberLotteryDataEntityWithNullCheckForUpdate(numberLotteryDataEntity.getId());
            numberLotteryDataEntity.setIsCurrent(YesNoStatus.NO);
        }

        NumberLotteryDataEntity specifyNumberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndPhase(lotteryType, phase);
        if (specifyNumberLotteryDataEntity == null) {
            String msg = String.format("指定当前期，根据彩种[%s]、彩期[%s]获取指定彩期为空", lotteryType.getName(), phase);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        numberLotteryDataRepository.detach(specifyNumberLotteryDataEntity);

        specifyNumberLotteryDataEntity = this.getNumberLotteryDataEntityWithNullCheckForUpdate(specifyNumberLotteryDataEntity.getId());
        specifyNumberLotteryDataEntity.setIsCurrent(YesNoStatus.YES);
        return BeanMapping.map(specifyNumberLotteryDataEntity, NumberLotteryData.class);
    }

    @Override
    @Transactional
    public void updateResult(LotteryType lotteryType, String phase, String result) throws NumberLotteryDataException {
        NumberLotteryDataEntity numberLotteryDataEntity = numberLotteryDataRepository.findByLotteryTypeAndPhase(lotteryType, phase);
        if (numberLotteryDataEntity == null) {
            String msg = String.format("指定当前期，根据彩种[%s]、彩期[%s]获取指定彩期为空", lotteryType.getName(), phase);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        numberLotteryDataRepository.detach(numberLotteryDataEntity);
        numberLotteryDataEntity = this.getNumberLotteryDataEntityWithNullCheckForUpdate(numberLotteryDataEntity.getId());

        try {
            Assert.isTrue(StringUtils.isNotBlank(result), "开奖结果为空, 无法更新开奖结果");
            Assert.isTrue(Objects.equals(numberLotteryDataEntity.getPhaseStatus(), PhaseStatus.CLOSED), String.format("彩期[phase=%s]状态不为[%s], 无法更新开奖结果", phase, PhaseStatus.CLOSED.getName()));
        } catch (Exception e) {
            throw new NumberLotteryDataException(e.getMessage());
        }

        PhaseStatus fromStatus = numberLotteryDataEntity.getPhaseStatus();
        PhaseStatus toStatus = PhaseStatus.RESULT_SET;

        try {
            PhaseStatus.checkStatusFlow(fromStatus, toStatus);
        } catch (StatusFlowException e) {
            String msg = String.format("更新状态失败, id=%s, fromStatus=%s, toStatus=%s", numberLotteryDataEntity.getId(), fromStatus.getName(), toStatus.getName());
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }

        numberLotteryDataEntity.setResult(result);
        numberLotteryDataEntity.setPhaseStatus(toStatus);
        numberLotteryDataEntity.setResultTime(LocalDateTime.now());
    }

    @Override
    public List<NumberLotteryData> findNearestPhaseList(LotteryType lotteryType, int prePhases, int nextPhases) throws NumberLotteryDataException {
        List<NumberLotteryData> numberLotteryDataList = Lists.newArrayList();

        NumberLotteryData currentPhase = this.getCurrentPhase(lotteryType);

        List<NumberLotteryData> prePhaseList = this.findPreviousPhaseList(lotteryType, currentPhase.getPhase(), prePhases);
        numberLotteryDataList.addAll(prePhaseList);

        numberLotteryDataList.add(currentPhase);

        List<NumberLotteryData> nextPhaseList = this.findNextPhaseList(lotteryType, currentPhase.getPhase(), nextPhases);
        numberLotteryDataList.addAll(nextPhaseList);

        numberLotteryDataList.sort(Comparator.comparing(NumberLotteryData::getPhase));
        return numberLotteryDataList;
    }

    @Override
    public List<NumberLotteryData> findPreviousPhaseList(LotteryType lotteryType, String phase, int n) throws NumberLotteryDataException {
        Assert.isTrue(lotteryType != null, "获取前n期，彩种不能为空");
        Assert.isTrue(StringUtils.isNotBlank(phase), "获取前n期，彩期不能为空");
        Assert.isTrue(!Objects.equals(n, 0), "获取前n期，期数不能为0");

        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                .filterLessThan(QNumberLotteryData.phase, phase);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                .paging(0, n)
                .addOrder(QNumberLotteryData.phase, PageOrderType.DESC);

        ApiResponse<NumberLotteryData> apiResponse = this.findAll(apiRequest, apiRequestPage);
        if (apiResponse.getPagedData() == null || apiResponse.getPagedData().isEmpty()) {
            String msg = String.format("获取彩种[%s]、彩期[%s]前%s期为空", lotteryType.getName(), phase, n);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        return (List<NumberLotteryData>)apiResponse.getPagedData();
    }

    @Override
    public List<NumberLotteryData> findNextPhaseList(LotteryType lotteryType, String phase, int n) throws NumberLotteryDataException {
        Assert.isTrue(lotteryType != null, "获取后n期，彩种不能为空");
        Assert.isTrue(StringUtils.isNotBlank(phase), "获取后n期，彩期不能为空");
        Assert.isTrue(!Objects.equals(n, 0), "获取后n期，期数不能为0");

        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                .filterGreaterThan(QNumberLotteryData.phase, phase);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                .paging(0, n)
                .addOrder(QNumberLotteryData.phase, PageOrderType.ASC);

        ApiResponse<NumberLotteryData> apiResponse = this.findAll(apiRequest, apiRequestPage);
        if (apiResponse.getPagedData() == null || apiResponse.getPagedData().isEmpty()) {
            String msg = String.format("获取彩种[%s]、彩期[%s]后%s期为空", lotteryType.getName(), phase, n);
            logger.error(msg);
            throw new NumberLotteryDataException(msg);
        }
        return (List<NumberLotteryData>)apiResponse.getPagedData();
    }
}
