package com.qatang.team.fetcher.service.impl;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.exception.StatusFlowException;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.entity.ProxyDataEntity;
import com.qatang.team.fetcher.exception.ProxyDataException;
import com.qatang.team.fetcher.repository.ProxyDataRepository;
import com.qatang.team.fetcher.service.ProxyDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author wangzhiliang
 */
@Service
public class ProxyDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements ProxyDataInternalService {

    @Autowired
    private ProxyDataRepository proxyDataRepository;

    protected ProxyDataEntity getProxyDataEntityWithNullCheckForUpdate(Long proxyDataId) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOneForUpdate(proxyDataId);
        if (proxyDataEntity == null) {
            String msg = String.format("未获取到代理数据：proxyDataId=%s", proxyDataId);
            logger.error(msg);
            throw new ProxyDataException(msg);
        }
        return proxyDataEntity;
    }

    protected ProxyDataEntity updateStatus(Long id, ProxyValidateStatus toStatus) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOne(id);
        if (proxyDataEntity == null) {
            String msg = String.format("更新代理数据检查状态异常：未查询到代理数据, proxyDataId=%s", id);
            logger.error(msg);
            throw new ProxyDataException(msg);
        }

        proxyDataRepository.detach(proxyDataEntity);

        proxyDataEntity = this.getProxyDataEntityWithNullCheckForUpdate(id);

        ProxyValidateStatus fromStatus = proxyDataEntity.getProxyValidateStatus();
        try {
            ProxyValidateStatus.checkStatusFlow(fromStatus, toStatus);
        } catch (StatusFlowException e) {
            String msg = String.format("更新代理数据检查状态异常：不允许更新状态, id=%s, fromStatus=%s, toStatus=%s", id, fromStatus.getName(), toStatus.getName());
            logger.error(msg);
            throw new ProxyDataException(msg);
        }

        proxyDataEntity.setProxyValidateStatus(toStatus);
        return proxyDataEntity;
    }

    @Override
    @Transactional
    public ProxyData save(ProxyData proxyData) throws ProxyDataException {
        logger.info("新建代理数据：开始执行");

        logger.info("新建代理数据：将ProxyData转换为ProxyDataEntity");
        ProxyDataEntity proxyDataEntity = BeanMapping.map(proxyData, ProxyDataEntity.class);

        logger.info("新建代理数据：保存ProxyDataEntity");
        proxyDataEntity = proxyDataRepository.save(proxyDataEntity);

        logger.info("新建代理数据：将ProxyDataEntity转换为ProxyData作为返回结果");
        ProxyData proxyDataResult = BeanMapping.map(proxyDataEntity, ProxyData.class);

        logger.info("新建代理数据：结束执行");
        return proxyDataResult;
    }

    @Override
    public ApiResponse<ProxyData> findAll(ApiRequest request, ApiRequestPage requestPage) throws ProxyDataException {
        Page<ProxyDataEntity> proxyDataEntityPage = proxyDataRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(proxyDataEntityPage, ProxyData.class);
    }

    @Override
    public ProxyData get(Long id) throws ProxyDataException {
        logger.info("按id获取代理数据, proxyDataId={}", id);
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOne(id);
        if (proxyDataEntity == null) {
            String msg = String.format("按id获取代理数据未获取到代理数据, proxyDataId=%s", id);
            logger.error(msg);
            throw new ProxyDataException(msg);
        }
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updateWaitingTestStatus(Long id) throws ProxyDataException {
        ProxyValidateStatus toStatus = ProxyValidateStatus.WAITING_TEST;
        ProxyDataEntity proxyDataEntity = updateStatus(id, toStatus);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updateTestingStatus(Long id) throws ProxyDataException {
        ProxyValidateStatus toStatus = ProxyValidateStatus.TESTING;
        ProxyDataEntity proxyDataEntity = updateStatus(id, toStatus);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updatePassStatus(Long id) throws ProxyDataException {
        ProxyValidateStatus toStatus = ProxyValidateStatus.PASS;
        ProxyDataEntity proxyDataEntity = updateStatus(id, toStatus);
        proxyDataEntity.setFailedCount(0);
        proxyDataEntity.setScore(GlobalConstants.PROXY_INIT_SCORE);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updateFailedStatus(Long id) throws ProxyDataException {
        ProxyValidateStatus toStatus = ProxyValidateStatus.FAILED;
        ProxyDataEntity proxyDataEntity = updateStatus(id, toStatus);
        proxyDataEntity.setFailedCount(proxyDataEntity.getFailedCount() + 1);
        proxyDataEntity.setScore(0);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updateObsoletedStatus(Long id) throws ProxyDataException {
        ProxyValidateStatus toStatus = ProxyValidateStatus.OBSOLETED;
        ProxyDataEntity proxyDataEntity = updateStatus(id, toStatus);
        proxyDataEntity.setScore(0);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updateBeginTestTime(Long id, LocalDateTime beginTestTime) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOne(id);
        if (proxyDataEntity == null) {
            throw new ProxyDataException(String.format("更新代理数据检查状态异常：未查询到代理数据, proxyDataId=%s", id));
        }

        proxyDataRepository.detach(proxyDataEntity);

        proxyDataEntity = this.getProxyDataEntityWithNullCheckForUpdate(id);
        proxyDataEntity.setBeginTestTime(beginTestTime);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public ProxyData updateEndTestTime(Long id, LocalDateTime endTestTime) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOne(id);
        if (proxyDataEntity == null) {
            throw new ProxyDataException(String.format("更新代理数据检查状态异常：未查询到代理数据, proxyDataId=%s", id));
        }

        proxyDataRepository.detach(proxyDataEntity);

        proxyDataEntity = this.getProxyDataEntityWithNullCheckForUpdate(id);
        proxyDataEntity.setEndTestTime(endTestTime);
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    public ProxyData getByHostAndPort(String host, int port) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findByHostAndPort(host, port);
        if (proxyDataEntity == null) {
            String msg = String.format("根据代理地址[%s]、代理端口[%s]获取代理数据为空", host, port);
            logger.error(msg);
            throw new ProxyDataException(msg);
        }
        return BeanMapping.map(proxyDataEntity, ProxyData.class);
    }

    @Override
    @Transactional
    public void increaseScore(Long id, int score) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOne(id);
        if (proxyDataEntity == null) {
            throw new ProxyDataException(String.format("更新代理数据检查状态异常：未查询到代理数据, proxyDataId=%s", id));
        }

        proxyDataRepository.detach(proxyDataEntity);

        proxyDataEntity = this.getProxyDataEntityWithNullCheckForUpdate(id);

        if (!proxyDataEntity.getProxyValidateStatus().equals(ProxyValidateStatus.PASS)) {
            String msg = String.format("加分操作对应代理状态必须为(%s)", ProxyValidateStatus.PASS.getName());
            logger.error(msg);
            throw new ProxyDataException(msg);
        }

        proxyDataEntity.setScore(proxyDataEntity.getScore() + score);
    }

    @Override
    @Transactional
    public void decreaseScore(Long id, int score) throws ProxyDataException {
        ProxyDataEntity proxyDataEntity = proxyDataRepository.findOne(id);
        if (proxyDataEntity == null) {
            throw new ProxyDataException(String.format("更新代理数据检查状态异常：未查询到代理数据, proxyDataId=%s", id));
        }

        proxyDataRepository.detach(proxyDataEntity);

        proxyDataEntity = this.getProxyDataEntityWithNullCheckForUpdate(id);

        if (!proxyDataEntity.getProxyValidateStatus().equals(ProxyValidateStatus.PASS)) {
            String msg = String.format("减分操作对应代理状态必须为(%s)", ProxyValidateStatus.PASS.getName());
            logger.error(msg);
            throw new ProxyDataException(msg);
        }
        int currentScore = proxyDataEntity.getScore();

        int result = currentScore - score;
        if (result <= 0) {
            result = 0;
            proxyDataEntity.setProxyValidateStatus(ProxyValidateStatus.WAITING_TEST);
        }
        proxyDataEntity.setScore(result);
    }
}
