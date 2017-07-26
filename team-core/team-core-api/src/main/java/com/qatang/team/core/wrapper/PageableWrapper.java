package com.qatang.team.core.wrapper;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;

/**
 * @author jinsheng
 */
public class PageableWrapper implements BaseWrapper {
    private static final long serialVersionUID = 5007146955472885893L;
    private ApiRequest request;
    private ApiRequestPage requestPage;

    private PageableWrapper() {
        
    }

    public PageableWrapper(ApiRequest request, ApiRequestPage apiRequestPage) {
        this.request = request;
        this.requestPage = apiRequestPage;
    }

    public ApiRequest getRequest() {
        return request;
    }

    public ApiRequestPage getRequestPage() {
        return requestPage;
    }
}
