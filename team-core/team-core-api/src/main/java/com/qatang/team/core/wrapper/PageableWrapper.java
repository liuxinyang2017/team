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

    public ApiRequest getRequest() {
        return request;
    }

    public void setRequest(ApiRequest request) {
        this.request = request;
    }

    public ApiRequestPage getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(ApiRequestPage requestPage) {
        this.requestPage = requestPage;
    }
}
