package com.qatang.team.data.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

import java.util.List;

/**
 * @author qatang
 */
public interface NumberLotteryDataInternalService extends BaseInternalSerivce {
    /**
     * 创建
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData save(NumberLotteryData numberLotteryData) throws NumberLotteryDataException;

    /**
     * 更新
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData update(NumberLotteryData numberLotteryData) throws NumberLotteryDataException;

    /**
     * 获取数字彩彩果
     * @param id 编码
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData get(Long id) throws NumberLotteryDataException;

    /**
     * 数字彩彩果自定义查询
     * @param request 自定义组合查询条件
     * @param requestPage 分页和排序条件
     * @return 分页组织的数字彩彩果查询列表
     * @throws NumberLotteryDataException
     */
    ApiResponse<NumberLotteryData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryDataException;

    /**
     * 获取数字彩彩果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getByLotteryTypeAndPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException;

    /**
     * 根据彩种获取当前期
     * @param lotteryType 彩种
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getCurrentPhase(LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 根据彩种获取当前期的上一期
     * @param lotteryType 彩种
     * @return 当前期的上一期
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getPreviousPhase(LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 根据彩种获取指定期的上一期
     * @param lotteryType 彩种
     * @param phase 指定期
     * @return 指定期的上一期
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getPreviousPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException;

    /**
     * 根据彩种获取当前期的下一期
     * @param lotteryType 彩种
     * @return 当前期的下一期
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getNextPhase(LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 根据彩种获取指定期的下一期
     * @param lotteryType 彩种
     * @param phase 指定期
     * @return 指定期的下一期
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getNextPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException;

    /**
     * 更新彩期状态
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param toStatus 目标状态
     * @param checkStatus 检查状态
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData updatePhaseStatus(LotteryType lotteryType, String phase, PhaseStatus toStatus, PhaseStatus checkStatus) throws NumberLotteryDataException;

    /**
     * 切换当前期
     * @param lotteryType 彩种
     * @throws NumberLotteryDataException
     */
    void switchCurrentPhase(LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 指定当前期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @throws NumberLotteryDataException
     */
    void specifyCurrentPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException;

    /**
     * 更新开奖结果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param result 开奖号码
     * @throws NumberLotteryDataException
     */
    void updateResult(LotteryType lotteryType, String phase, String result) throws NumberLotteryDataException;

    /**
     * 获取当前彩期前后n期数字彩彩果列表
     * @param lotteryType 彩种
     * @param prePhases 前n期
     * @param nextPhases 后n期
     * @throws NumberLotteryDataException
     */
    List<NumberLotteryData> findNearestPhaseList(LotteryType lotteryType, int prePhases, int nextPhases) throws NumberLotteryDataException;

    /**
     * 获取指定彩期之前n期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param n 前n期
     * @throws NumberLotteryDataException
     */
    List<NumberLotteryData> findPreviousPhaseList(LotteryType lotteryType, String phase, int n) throws NumberLotteryDataException;

    /**
     * 获取指定彩期之后n期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param n 后n期
     * @throws NumberLotteryDataException
     */
    List<NumberLotteryData> findNextPhaseList(LotteryType lotteryType, String phase, int n) throws NumberLotteryDataException;
}
