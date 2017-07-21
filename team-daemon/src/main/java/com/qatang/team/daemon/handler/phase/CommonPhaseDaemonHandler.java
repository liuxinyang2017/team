package com.qatang.team.daemon.handler.phase;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.daemon.handler.AbstractDefaultDaemonHandler;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QDaemonEventTask;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 按彩种和彩期进行守护处理器
 * @author sunshow
 */
public class CommonPhaseDaemonHandler extends AbstractDefaultDaemonHandler {

    /**
     * 要守护的彩种
     */
    protected final LotteryType lotteryType;

    /**
     * 要守护的彩种的事件类型
     */
    protected final Set<DaemonEventType> daemonEventTypeSet;

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    public CommonPhaseDaemonHandler(LotteryType lotteryType, Set<DaemonEventType> daemonEventTypeSet) {
        this.lotteryType = lotteryType;
        this.daemonEventTypeSet = ImmutableSet.copyOf(daemonEventTypeSet);
    }

    @Override
    protected void dispatchTaskList(List<DaemonEventTask> taskList) {
        taskList.forEach(task -> {
            // 派发任务前先将task状态更新为 已派发未执行
            // 由于派发之后是异步执行, 会导致异步任务还没执行(task状态还没设置成 执行中),守护循环又去拿下一个任务(还是相同的任务), 造成重复派发
            daemonEventTaskApiService.updateStatus(task.getId(), DaemonEventStatus.SENDING, DaemonEventStatus.PENDING);

        });
    }

    @Override
    protected List<DaemonEventTask> findPersistentTaskList() {
        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QDaemonEventTask.lotteryType, lotteryType)
                .filterEqual(QDaemonEventTask.status, DaemonEventStatus.PENDING);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                .paging(0, 100)
                .addOrder(QDaemonEventTask.executeTime, PageOrderType.ASC)
                .addOrder(QDaemonEventTask.id, PageOrderType.ASC);
        return ApiPageRequestHelper.request(apiRequest, apiRequestPage, daemonEventTaskApiService::findAll);
    }

    @Override
    protected List<DaemonEventTask> findActualPendingTaskList() {

        logger.info("计算需要处理的彩期");
        List<NumberLotteryData> numberLotteryDataList = calculatePhaseList();

        logger.info("按彩期计算要守护的彩期事件任务");
        List<DaemonEventTask> allDaemonEventTaskList = Lists.newArrayList();
        numberLotteryDataList.forEach(numberLotteryData -> allDaemonEventTaskList.addAll(calculateTaskList(numberLotteryData)));

        logger.info("排除掉已过期的任务");
        LocalDateTime now = LocalDateTime.now();
        return allDaemonEventTaskList.stream().filter(task -> CoreDateUtils.convertFrom(task.getExecuteTime()).isAfter(now)).collect(Collectors.toList());
    }

    /**
     * 计算需要处理的彩期
     * 查找当前期, 当前期的上一期和下一期
     * @return 需要处理的彩期列表
     */
    protected List<NumberLotteryData> calculatePhaseList() {
        NumberLotteryData previousPhase = numberLotteryDataApiService.getPreviousPhase(lotteryType);
        NumberLotteryData currentPhase = numberLotteryDataApiService.getCurrentPhase(lotteryType);
        NumberLotteryData nextPhase = numberLotteryDataApiService.getNextPhase(lotteryType);

        List<NumberLotteryData> numberLotteryDataList = Lists.newArrayList();
        if (previousPhase != null) {
            numberLotteryDataList.add(previousPhase);
        }
        numberLotteryDataList.add(currentPhase);
        if (nextPhase != null) {
            numberLotteryDataList.add(nextPhase);
        }
        return numberLotteryDataList;
    }

    /**
     * 按彩期计算要守护的彩期事件任务
     * @param numberLotteryData 彩期对象
     * @return 指定期要守护的彩期事件任务列表
     */
    protected List<DaemonEventTask> calculateTaskList(NumberLotteryData numberLotteryData) {
        List<DaemonEventTask> daemonEventTaskList = Lists.newArrayList();
        for (DaemonEventType daemonEventType : daemonEventTypeSet) {
            DaemonEventTask daemonEventTask = new DaemonEventTask();
            daemonEventTask.setLotteryType(numberLotteryData.getLotteryType());
            daemonEventTask.setPhase(numberLotteryData.getPhase());
            daemonEventTask.setStatus(DaemonEventStatus.PENDING);
            daemonEventTask.setType(daemonEventType);

            LocalDateTime executeTime = null;
            switch (daemonEventType) {
                case PHASE_OPEN:
                    executeTime = numberLotteryData.getOpenTime();
                    break;
                case PHASE_CLOSE:
                    executeTime = numberLotteryData.getCloseTime();
                    break;
                case PHASE_CURRENT_SWITCH:
                    executeTime = numberLotteryData.getCloseTime();
                    break;
                default:
                    logger.error("不支持的彩期守护事件类型, 跳过处理, type={}", daemonEventType);
            }
            daemonEventTask.setExecuteTime(CoreDateUtils.convertTo(executeTime));
            daemonEventTaskList.add(daemonEventTask);
        }
        return daemonEventTaskList;
    }

    @Override
    protected boolean isSameTask(DaemonEventTask task1, DaemonEventTask task2) {
        try {
            Assert.isTrue(task1.getType() == task2.getType());
            Assert.isTrue(task1.getLotteryType() == task2.getLotteryType());
            Assert.isTrue(task1.getPhase().equals(task2.getPhase()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected Collection<DaemonEventTask> findPendingTaskOrderByExecuteTimeAsc() {
        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QDaemonEventTask.lotteryType, lotteryType)
                .filterEqual(QDaemonEventTask.status, DaemonEventStatus.PENDING);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                .paging(0, 100)
                .addOrder(QDaemonEventTask.executeTime, PageOrderType.ASC)
                .addOrder(QDaemonEventTask.id, PageOrderType.ASC);

        try {
            ApiResponse<DaemonEventTask> apiResponse = daemonEventTaskApiService.findAll(apiRequest, apiRequestPage);
            return apiResponse.getPagedData();
        } catch (Exception e) {
            logger.error("查找下一个执行的任务时间点出错, lotteryType=" + lotteryType, e);
            return null;
        }
    }
}
