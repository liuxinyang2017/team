package com.qatang.team.fetcher.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.fetcher.entity.ProxyDataEntity;

/**
 * @author wangzhiliang
 */
public interface ProxyDataRepository extends BaseRepository<ProxyDataEntity, Long> {

    /***
     * 根据代理地址，代理端口获取代理数据
     * @param host 代理地址
     * @param port 代理端口
     * @return 获取到的代理数据
     */
    ProxyDataEntity findByHostAndPort(String host, int port);
}
