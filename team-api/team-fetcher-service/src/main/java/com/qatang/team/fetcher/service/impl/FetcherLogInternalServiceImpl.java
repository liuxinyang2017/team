package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.entity.FetcherLogEntity;
import com.qatang.team.fetcher.exception.FetcherLogException;
import com.qatang.team.fetcher.repository.FetcherLogRepository;
import com.qatang.team.fetcher.service.FetcherLogInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author wp
 * @since 2017/7/20
 */
@Service
public class FetcherLogInternalServiceImpl extends AbstractBaseInternalServiceImpl implements FetcherLogInternalService {

    @Autowired
    private FetcherLogRepository fetcherLogRepository;

    protected FetcherLogEntity getFetcherLogEntityWithNullCheckForUpdate(Long fetcherLogId) throws FetcherLogException {
        return fetcherLogRepository.findOneForUpdate(fetcherLogId);
    }

    @Override
    @Transactional
    public FetcherLog save(FetcherLog fetcherLog) throws FetcherLogException {
        logger.info("新建抓取日志：开始执行");

        logger.info("新建抓取日志：将FetcherLog转换为FetcherLogEntity");
        FetcherLogEntity fetcherLogEntity = BeanMapping.map(fetcherLog,FetcherLogEntity.class);

        logger.info("新建抓取日志：保存FetcherLogEntity");
        fetcherLogEntity = fetcherLogRepository.save(fetcherLogEntity);

        logger.info("新建抓取日志：将FetcherLogEntity转换为FetcherLog作为返回结果");
        FetcherLog fetcherLogResult = BeanMapping.map(fetcherLogEntity, FetcherLog.class);

        logger.info("新建抓取日志：结束执行");
        return fetcherLogResult;
    }

    @Override
    public ApiResponse<FetcherLog> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetcherLogException {
        Page<FetcherLogEntity> proxyDataEntityPage = fetcherLogRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(proxyDataEntityPage, FetcherLog.class);
    }

    @Override
    public FetcherLog get(Long id) throws FetcherLogException {
        logger.info("按id获取抓取日志, fetcherLogId={}", id);
        FetcherLogEntity fetcherLogEntity = fetcherLogRepository.findOne(id);
        if (fetcherLogEntity == null) {
            throw new FetcherLogException(String.format("按id获取抓取日志未获取到抓取日志, fetcherLogId=%s", id));
        }
        return BeanMapping.map(fetcherLogEntity, FetcherLog.class);
    }

    @Override
    @Transactional
    public FetcherLog update(FetcherLog fetcherLog) throws FetcherLogException {
        logger.info("修改抓取日志服务：开始执行");

        Assert.notNull(fetcherLog.getId(), "抓取日志编号不能为空");

        logger.info("修改抓取日志服务：锁行查询FetcherLogEntity");
        FetcherLogEntity fetcherLogEntity = getFetcherLogEntityWithNullCheckForUpdate(fetcherLog.getId());
        copyUpdatableField(fetcherLogEntity, fetcherLog);

        logger.info("修改抓取日志服务：将FetcherLogEntity转换为FetcherLog作为返回结果");
        FetcherLog fetcherLogResult = BeanMapping.map(fetcherLogEntity, FetcherLog.class);

        logger.info("修改抓取日志服务：结束执行");
        return fetcherLogResult;
    }
}
