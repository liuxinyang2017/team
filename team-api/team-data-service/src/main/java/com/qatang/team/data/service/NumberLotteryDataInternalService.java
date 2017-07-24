package com.qatang.team.data.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

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
}
