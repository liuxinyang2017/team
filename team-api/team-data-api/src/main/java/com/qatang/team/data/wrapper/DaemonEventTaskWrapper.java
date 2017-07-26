package com.qatang.team.data.wrapper;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.wrapper.AbstractBaseWrapper;
import com.qatang.team.data.bean.QDaemonEventTask;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.lottery.LotteryType;

import java.util.Objects;

/**
 * 守护事件任务对象数据
 * @author jinsheng
 */
public class DaemonEventTaskWrapper extends AbstractBaseWrapper {
    private static final long serialVersionUID = -3776988026790708292L;
    private Long id;
    private LotteryType lotteryType;
    private DaemonEventStatus status;
    private DaemonEventStatus toStatus;
    private DaemonEventStatus checkStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public DaemonEventStatus getStatus() {
        return status;
    }

    public void setStatus(DaemonEventStatus status) {
        this.status = status;
    }

    public DaemonEventStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(DaemonEventStatus toStatus) {
        this.toStatus = toStatus;
    }

    public DaemonEventStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(DaemonEventStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public ApiRequest convertRequest() {
        ApiRequest apiRequest = ApiRequest.newInstance();

        if (id != null) {
            apiRequest.filterEqual(QDaemonEventTask.id, id);
        }

        if (lotteryType != null && !Objects.equals(lotteryType, LotteryType.ALL)) {
            apiRequest.filterEqual(QDaemonEventTask.lotteryType, lotteryType);
        }

        if (status != null && !Objects.equals(status, DaemonEventStatus.ALL)) {
            apiRequest.filterEqual(QDaemonEventTask.status, status);
        }
        
        return apiRequest;
    }
}
