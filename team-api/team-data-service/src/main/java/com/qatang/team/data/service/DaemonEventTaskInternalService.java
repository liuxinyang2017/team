package com.qatang.team.data.service;

import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskException;

/**
 * @author jinsheng
 */
public interface DaemonEventTaskInternalService extends BaseInternalSerivce {

    /**
     * 守护事件任务对象
     * @param id 编码
     * @return 守护事件任务对象
     * @throws DaemonEventTaskException
     */
    DaemonEventTask get(Long id) throws DaemonEventTaskException;
}
