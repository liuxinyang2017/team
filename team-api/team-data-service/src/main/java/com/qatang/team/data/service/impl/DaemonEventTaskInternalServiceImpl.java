package com.qatang.team.data.service.impl;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.impl.AbstractBaseInternalServiceImpl;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.entity.DaemonEventTaskEntity;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.data.repository.DaemonEventTaskRepository;
import com.qatang.team.data.service.DaemonEventTaskInternalService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.exception.StatusFlowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author jinsheng
 */
@Service
public class DaemonEventTaskInternalServiceImpl extends AbstractBaseInternalServiceImpl implements DaemonEventTaskInternalService {

    @Autowired
    private DaemonEventTaskRepository daemonEventTaskRepository;

    protected DaemonEventTaskEntity getDaemonEventTaskEntityWithNullCheckForUpdate(Long id) throws DaemonEventTaskException {
        DaemonEventTaskEntity daemonEventTaskEntity = daemonEventTaskRepository.findOneForUpdate(id);
        if (daemonEventTaskEntity == null) {
            throw new DaemonEventTaskException(String.format("未获取守护事件任务对象：daemonEventTaskId=%s", id));
        }
        return daemonEventTaskEntity;
    }

    @Override
    public DaemonEventTask save(DaemonEventTask daemonEventTask) throws DaemonEventTaskException {
        logger.info("新建守护事件任务对象：开始执行");

        logger.info("新建守护事件任务对象：将DaemonEventTask转换为DaemonEventTaskEntity");
        DaemonEventTaskEntity daemonEventTaskEntity = BeanMapping.map(daemonEventTask, DaemonEventTaskEntity.class);

        logger.info("新建守护事件任务对象：保存DaemonEventTaskEntity");
        daemonEventTaskEntity = daemonEventTaskRepository.save(daemonEventTaskEntity);

        logger.info("新建守护事件任务对象：将DaemonEventTaskEntity转换为DaemonEventTask作为返回结果");
        DaemonEventTask daemonEventTaskResult = BeanMapping.map(daemonEventTaskEntity, DaemonEventTask.class);

        logger.info("新建守护事件任务对象：结束执行");
        return daemonEventTaskResult;
    }

    @Override
    public DaemonEventTask update(DaemonEventTask daemonEventTask) throws DaemonEventTaskException {
        logger.info("修改守护事件任务对象服务：开始执行");

        Assert.notNull(daemonEventTask.getId(), "守护事件任务对象服务id不能为空");

        logger.info("修改守护事件任务对象服务：锁行查询DaemonEventTaskEntity");

        DaemonEventTaskEntity daemonEventTaskEntity = daemonEventTaskRepository.findOne(daemonEventTask.getId());

        daemonEventTaskRepository.detach(daemonEventTaskEntity);

        daemonEventTaskEntity = this.getDaemonEventTaskEntityWithNullCheckForUpdate(daemonEventTask.getId());
        copyUpdatableField(daemonEventTaskEntity, daemonEventTask);

        logger.info("修改守护事件任务对象服务：将DaemonEventTaskEntity转换为DaemonEventTask作为返回结果");
        DaemonEventTask daemonEventTaskResult = BeanMapping.map(daemonEventTaskEntity, DaemonEventTask.class);

        logger.info("修改守护事件任务对象服务：结束执行");
        return daemonEventTaskResult;
    }

    @Override
    public DaemonEventTask get(Long id) throws DaemonEventTaskException {
        DaemonEventTaskEntity daemonEventTaskEntity = daemonEventTaskRepository.findOne(id);
        if (daemonEventTaskEntity == null) {
            String msg = String.format("根据主键[%s]获取守护事件任务对象为空", id);
            logger.error(msg);
            throw new DaemonEventTaskException(msg);
        }
        return BeanMapping.map(daemonEventTaskEntity, DaemonEventTask.class);
    }

    @Override
    public ApiResponse<DaemonEventTask> findAll(ApiRequest request, ApiRequestPage requestPage) throws DaemonEventTaskException {
        Page<DaemonEventTaskEntity> daemonEventTaskEntityPage = daemonEventTaskRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(daemonEventTaskEntityPage, DaemonEventTask.class);
    }

    @Override
    public DaemonEventTask updateStatus(Long id, DaemonEventStatus toStatus, DaemonEventStatus checkStatus) throws DaemonEventTaskException {
        DaemonEventTaskEntity daemonEventTaskEntity = daemonEventTaskRepository.findOne(id);
        if (daemonEventTaskEntity == null) {
            String msg = String.format("更新守护事件状态，根据主键[%s]获取守护事件任务对象为空", id);
            logger.error(msg);
            throw new DaemonEventTaskException(msg);
        }

        daemonEventTaskRepository.detach(daemonEventTaskEntity);

        daemonEventTaskEntity = this.getDaemonEventTaskEntityWithNullCheckForUpdate(daemonEventTaskEntity.getId());
        DaemonEventStatus status = daemonEventTaskEntity.getStatus();
        if (checkStatus != null && checkStatus != status) {
            String msg = String.format("更新状态不一致, id=%s, toStatus=%s, checkStatus=%s, currentStatus=%s", daemonEventTaskEntity.getId(), toStatus.getName(), checkStatus.getName(), status.getName());
            logger.error(msg);
            throw new DaemonEventTaskException(msg);
        }
        try {
            DaemonEventStatus.checkStatusFlow(status, toStatus);
        } catch (StatusFlowException e) {
            String msg = String.format("更新状态失败, id=%s, fromStatus=%s, toStatus=%s", daemonEventTaskEntity.getId(), status.getName(), toStatus.getName());
            logger.error(msg);
            throw new DaemonEventTaskException(msg);
        }
        daemonEventTaskEntity.setStatus(toStatus);

        return BeanMapping.map(daemonEventTaskEntity, DaemonEventTask.class);
    }
}
