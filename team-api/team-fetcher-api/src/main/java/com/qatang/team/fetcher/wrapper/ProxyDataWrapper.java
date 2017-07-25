package com.qatang.team.fetcher.wrapper;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.wrapper.AbstractBaseWrapper;

/**
 * 代理数据
 * @author qatang
 */
public class ProxyDataWrapper extends AbstractBaseWrapper {

    private static final long serialVersionUID = -2604980888160497182L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ApiRequest convertRequest() {
        ApiRequest apiRequest = ApiRequest.newInstance();

        if (id != null) {
            apiRequest.filterEqual("id", id);
        }
        
        return apiRequest;
    }
}
