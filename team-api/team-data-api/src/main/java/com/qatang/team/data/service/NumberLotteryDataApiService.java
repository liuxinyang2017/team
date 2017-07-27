package com.qatang.team.data.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数字彩彩果服务
 * @author qatang
 */
@FeignClient("team-data-service")
@RequestMapping("/data/numberLotteryData")
public interface NumberLotteryDataApiService {

    /**
     * 创建
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     * @throws NumberLotteryDataException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryData create(@RequestBody NumberLotteryData numberLotteryData) throws NumberLotteryDataException;

    /**
     * 修改数字彩彩果
     * @param numberLotteryData 数字彩彩果对象
     * @return 修改后的数字彩彩果对象
     * @throws NumberLotteryDataException
     */

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryData update(@RequestBody NumberLotteryData numberLotteryData) throws NumberLotteryDataException;

    /***
     * 获取数字彩彩果信息
     * @param numberLotteryDataId 数字彩彩果id
     * @return 数字彩彩果对象
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryData get(@RequestParam(value = "id") Long numberLotteryDataId) throws NumberLotteryDataException;

    /**
     * 根据彩种彩期获取彩期对象
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 数字彩彩果对象
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhase", method = RequestMethod.GET)
    NumberLotteryData getByLotteryTypeAndPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) throws NumberLotteryDataException;

    /**
     * 数字彩彩果信息自定义查询
     * @param pageableWrapper 自定义组合查询条件、分页和排序条件
     * @return 分页组织的数字彩彩果信息查询列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<NumberLotteryData> findAll(@RequestBody PageableWrapper pageableWrapper) throws NumberLotteryDataException;

    /**
     * 根据彩种获取当前期
     * @param lotteryType 彩种
     * @return 当前彩期
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/getCurrentPhase", method = RequestMethod.GET)
    NumberLotteryData getCurrentPhase(@RequestParam("lotteryType") LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 根据彩种获取当前期的上一期
     * @param lotteryType 彩种
     * @return 当前期的上一期
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/getPreviousPhaseByLotteryType", method = RequestMethod.GET)
    NumberLotteryData getPreviousPhase(@RequestParam("lotteryType") LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 根据彩种获取指定期的上一期
     * @param lotteryType 彩种
     * @param phase 指定期
     * @return 指定期的上一期
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/getPreviousPhaseByLotteryTypeAndPhase", method = RequestMethod.GET)
    NumberLotteryData getPreviousPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) throws NumberLotteryDataException;

    /**
     * 根据彩种获取当前期的下一期
     * @param lotteryType 彩种
     * @return 当前期的下一期
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/getNextPhaseByLotteryType", method = RequestMethod.GET)
    NumberLotteryData getNextPhase(@RequestParam("lotteryType") LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 根据彩种获取指定期的下一期
     * @param lotteryType 彩种
     * @return 指定期的下一期
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/getNextPhaseByLotteryTypeAndPhase", method = RequestMethod.GET)
    NumberLotteryData getNextPhase(@RequestParam("lotteryType") LotteryType lotteryType, String phase) throws NumberLotteryDataException;

    /**
     * 更新彩期状态
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param toStatus 目标状态
     * @param checkStatus 检查状态
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    void updateStatus(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("toStatus") PhaseStatus toStatus, @RequestParam("checkStatus") PhaseStatus checkStatus) throws NumberLotteryDataException;

    /**
     * 切换当前期
     * @param lotteryType 彩种
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/switchCurrentPhase", method = RequestMethod.POST)
    void switchCurrentPhase(@RequestParam("lotteryType") LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 指定当前期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/specifyCurrentPhase", method = RequestMethod.POST)
    NumberLotteryData specifyCurrentPhase(@RequestParam(value = "lotteryType") LotteryType lotteryType, @RequestParam(value = "phase") String phase) throws NumberLotteryDataException;

    /**
     * 更新彩期开奖结果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param result 开奖结果
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/updateResult", method = RequestMethod.POST)
    void updateResult(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("result") String result) throws NumberLotteryDataException;

    /**
     * 获取当前彩期前后n期彩期列表
     * @param lotteryType 彩种
     * @param prePhases 前n期
     * @param nextPhases 后n期
     * @return 当前彩期前后n期数字彩彩果列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findNearestPhaseList", method = RequestMethod.GET)
    List<NumberLotteryData> findNearestPhaseList(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("prePhases") int prePhases, @RequestParam("nextPhases") int nextPhases) throws NumberLotteryDataException;

    /**
     * 获取指定彩期之前n期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param n 前n期
     * @return 指定彩期之前n期数字彩彩果列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findPreviousPhaseList", method = RequestMethod.GET)
    List<NumberLotteryData> findPreviousPhaseList(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("n") int n) throws NumberLotteryDataException;

    /**
     * 获取指定彩期之后n期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param n 后n期
     * @return 指定彩期之后n期数字彩彩果列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findNextPhaseList", method = RequestMethod.GET)
    List<NumberLotteryData> findNextPhaseList(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("n") int n) throws NumberLotteryDataException;
}
