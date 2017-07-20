package com.qatang.team.fetcher.service.impl;

import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.entity.ProxyDataEntity;
import com.qatang.team.fetcher.exception.ProxyDataException;
import com.qatang.team.fetcher.repository.ProxyDataRepository;
import com.qatang.team.fetcher.service.ProxyDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author wangzhiliang
 */
@Service
public class ProxyDataInternalServiceImpl extends AbstractBaseInternalServiceImpl implements ProxyDataInternalService {

    @Autowired
    private ProxyDataRepository proxyDataRepository;

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
}
