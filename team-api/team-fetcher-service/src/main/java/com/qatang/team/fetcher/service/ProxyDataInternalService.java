package com.qatang.team.fetcher.service;

import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.exception.ProxyDataException;

/**
 * 代理数据内部服务接口
 * @author wangzhiliang
 */
public interface ProxyDataInternalService extends BaseInternalSerivce {

    /**
     * 创建代理数据
     * @param proxyData 代理数据
     * @return 保存后的代理数据
     * @throws ProxyDataException 异常
     */
    ProxyData save(ProxyData proxyData) throws ProxyDataException;
}
