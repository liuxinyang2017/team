package com.qatang.team.data.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.data.service.DaemonEventTaskInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jinsheng
 */
@Service
public class DaemonEventTaskInternalServiceImpl extends AbstractBaseInternalServiceImpl implements DaemonEventTaskInternalService {

    @Override
    public DaemonEventTask get(Long id) throws DaemonEventTaskException {
        return null;
    }

    @Override
    public ApiResponse<DaemonEventTask> findAll(ApiRequest request, ApiRequestPage requestPage) throws DaemonEventTaskException {
        return null;
    }
}
