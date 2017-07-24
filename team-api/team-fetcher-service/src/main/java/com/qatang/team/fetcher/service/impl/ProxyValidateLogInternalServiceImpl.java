package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.entity.ProxyValidateLogEntity;
import com.qatang.team.fetcher.exception.ProxyValidateLogException;
import com.qatang.team.fetcher.repository.ProxyValidateLogRepository;
import com.qatang.team.fetcher.service.ProxyValidateLogInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * 代理日志内部服务实现
 * @author wp
 * @since 2017/7/24
 */
@Service
public class ProxyValidateLogInternalServiceImpl extends AbstractBaseInternalServiceImpl implements ProxyValidateLogInternalService {

    @Autowired
    private ProxyValidateLogRepository proxyValidateLogRepository;

    protected ProxyValidateLogEntity getProxyValidateLogEntityWithNullCheckForUpdate(Long proxyValidateLogId) throws ProxyValidateLogException {
        return proxyValidateLogRepository.findOneForUpdate(proxyValidateLogId);
    }

    @Override
    @Transactional
    public ProxyValidateLog save(ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException {
        logger.info("新建代理日志：开始执行");

        logger.info("新建代理日志：将ProxyValidateLog转换为ProxyValidateLogEntity");
        ProxyValidateLogEntity proxyValidateLogEntity = BeanMapping.map(proxyValidateLog, ProxyValidateLogEntity.class);

        logger.info("新建代理日志：保存ProxyValidateLogEntity");
        proxyValidateLogEntity = proxyValidateLogRepository.save(proxyValidateLogEntity);

        logger.info("新建代理日志：将ProxyValidateLogEntity转换为ProxyValidateLog作为返回结果");
        ProxyValidateLog proxyValidateLogResult = BeanMapping.map(proxyValidateLogEntity, ProxyValidateLog.class);

        logger.info("新建代理日志：结束执行");
        return proxyValidateLogResult;
    }

    @Override
    public ApiResponse<ProxyValidateLog> findAll(ApiRequest request, ApiRequestPage requestPage) throws ProxyValidateLogException {
        Page<ProxyValidateLogEntity> proxyValidateLogEntityPage = proxyValidateLogRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(proxyValidateLogEntityPage, ProxyValidateLog.class);
    }

    @Override
    public ProxyValidateLog get(Long id) throws ProxyValidateLogException {
        logger.info("按id获取代理日志, proxyValidateLogId={}", id);
        ProxyValidateLogEntity proxyValidateLogEntity = proxyValidateLogRepository.findOne(id);
        if (proxyValidateLogEntity == null) {
            throw new ProxyValidateLogException(String.format("按id获取代理日志未获取到代理日志, proxyValidateLogId=%s", id));
        }
        return BeanMapping.map(proxyValidateLogEntity, ProxyValidateLog.class);
    }

    @Override
    @Transactional
    public ProxyValidateLog update(ProxyValidateLog proxyValidateLog) throws ProxyValidateLogException {
        logger.info("修改代理日志服务：开始执行");

        Assert.notNull(proxyValidateLog.getId(), "抓取日志编号不能为空");

        logger.info("修改代理日志服务：锁行查询ProxyValidateLogEntity");
        ProxyValidateLogEntity proxyValidateLogEntity = getProxyValidateLogEntityWithNullCheckForUpdate(proxyValidateLog.getId());
        copyUpdatableField(proxyValidateLogEntity, proxyValidateLog);

        logger.info("修改代理日志服务：将ProxyValidateLogEntity转换为ProxyValidateLog作为返回结果");
        ProxyValidateLog proxyValidateLogResult = BeanMapping.map(proxyValidateLogEntity, ProxyValidateLog.class);

        logger.info("修改代理日志服务：结束执行");
        return proxyValidateLogResult;
    }
}
