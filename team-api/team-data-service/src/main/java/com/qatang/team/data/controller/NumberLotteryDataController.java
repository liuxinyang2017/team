package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.data.service.NumberLotteryDataInternalService;
import com.qatang.team.data.service.NumberLotteryDetailDataInternalService;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangzhiliang
 */
@RestController
@RequestMapping("/data/numberLotteryData")
public class NumberLotteryDataController extends BaseController {

    @Autowired
    private NumberLotteryDataInternalService numberLotteryDataInternalService;

    @Autowired
    private NumberLotteryDetailDataInternalService numberLotteryDetailDataInternalService;

    /**
     * 创建数字彩彩果
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public NumberLotteryData create(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("开始创建数字彩开奖号码对象");
        return numberLotteryDataInternalService.save(numberLotteryData);
    }

    /**
     * 修改数字彩彩果
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public NumberLotteryData update(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("修改数字彩开奖号码对象");
        return numberLotteryDataInternalService.update(numberLotteryData);
    }

    /**
     * 获取数字彩彩果
     * @param numberLotteryDataId 数字彩彩果id
     * @return 数字彩彩果
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public NumberLotteryData get(@RequestParam("id") Long numberLotteryDataId) {
        logger.info("获取数字彩开奖号码对象信息");
        return numberLotteryDataInternalService.get(numberLotteryDataId);
    }

    /**
     * 根据彩种彩期获取彩期对象
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 数字彩彩果对象
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhase", method = RequestMethod.GET)
    public NumberLotteryData getByLotteryTypeAndPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) {
        logger.info("根据彩种、彩期获取数字彩开奖号码对象");
        return numberLotteryDataInternalService.getByLotteryTypeAndPhase(lotteryType, phase);
    }

    /**
     * 数字彩彩果信息自定义查询
     * @param pageableWrapper 自定义组合查询条件、分页和排序条件
     * @return 分页组织的数字彩彩果信息查询列表
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<NumberLotteryData> findAll(@RequestBody PageableWrapper pageableWrapper) {
        logger.info("自定义查询数字彩开奖号码对象信息");
        ApiRequest request = pageableWrapper.getRequest();
        ApiRequestPage requestPage = pageableWrapper.getRequestPage();

        for (ApiRequestFilter filter : request.getFilterList()) {
            String field = filter.getField();
            Object value = filter.getValue();
            List<Object> valueList = filter.getValueList();

            switch (field) {
                case "lotteryType":
                    if (value != null) {
                        value = LotteryType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> LotteryType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "phaseStatus":
                    if (value != null) {
                        value = PhaseStatus.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> PhaseStatus.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "isCurrent":
                    if (value != null) {
                        value = YesNoStatus.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> YesNoStatus.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "createdTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "updatedTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "openTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "closeTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "prizeTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "resultTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case "resultDetailTime":
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                default:

            }

        }
        return numberLotteryDataInternalService.findAll(request, requestPage);
    }

    /**
     * 根据彩种获取当前期
     * @param lotteryType 彩种
     * @return 当前彩期
     */
    @RequestMapping(value = "/getCurrentPhase", method = RequestMethod.GET)
    public NumberLotteryData getCurrentPhase(@RequestParam("lotteryType") LotteryType lotteryType) {
        logger.info("根据彩种获取当前期");
        return numberLotteryDataInternalService.getCurrentPhase(lotteryType);
    }

    /**
     * 根据彩种获取当前期的上一期
     * @param lotteryType 彩种
     * @return 当前期的上一期
     */
    @RequestMapping(value = "/getPreviousPhaseByLotteryType", method = RequestMethod.GET)
    public NumberLotteryData getPreviousPhase(@RequestParam("lotteryType") LotteryType lotteryType) {
        logger.info("开始获取彩种[{}]当前期的上一期", lotteryType.getName());
        return numberLotteryDataInternalService.getPreviousPhase(lotteryType);
    }

    /**
     * 根据彩种获取指定期的上一期
     * @param lotteryType 彩种
     * @param phase 指定期
     * @return 指定期的上一期
     */
    @RequestMapping(value = "/getPreviousPhaseByLotteryTypeAndPhase", method = RequestMethod.GET)
    public NumberLotteryData getPreviousPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) {
        logger.info("开始获取彩种[{}]指定期[{}]的上一期", lotteryType.getName(), phase);
        return numberLotteryDataInternalService.getPreviousPhase(lotteryType, phase);
    }

    /**
     * 根据彩种获取当前期的下一期
     * @param lotteryType 彩种
     * @return 当前期的下一期
     */
    @RequestMapping(value = "/getNextPhaseByLotteryType", method = RequestMethod.GET)
    public NumberLotteryData getNextPhase(@RequestParam LotteryType lotteryType) {
        logger.info("开始获取彩种[{}]当前期的下一期", lotteryType.getName());
        return numberLotteryDataInternalService.getNextPhase(lotteryType);
    }

    /**
     * 根据彩种获取指定期的下一期
     * @param lotteryType 彩种
     * @param phase 指定期
     * @return 指定期的下一期
     */
    @RequestMapping(value = "/getNextPhaseByLotteryTypeAndPhase", method = RequestMethod.GET)
    public NumberLotteryData getNextPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) {
        logger.info("开始获取彩种[{}]指定期[{}]的下一期", lotteryType.getName(), phase);
        return numberLotteryDataInternalService.getNextPhase(lotteryType, phase);
    }

    /**
     * 更新彩期状态
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param toStatus 目标状态
     * @param checkStatus 检查状态
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public NumberLotteryData updateStatus(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("toStatus") PhaseStatus toStatus, @RequestParam("checkStatus") PhaseStatus checkStatus) {
        logger.info("开始更新彩期状态");
        return numberLotteryDataInternalService.updateStatus(lotteryType, phase, toStatus, checkStatus);
    }

    /**
     * 切换当前期
     * @param lotteryType 彩种
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/switchCurrentPhase", method = RequestMethod.POST)
    public void switchCurrentPhase(@RequestParam("lotteryType") LotteryType lotteryType) {
        logger.info("开始切换彩种[{}]当前期", lotteryType.getName());
        numberLotteryDataInternalService.switchCurrentPhase(lotteryType);
    }

    /**
     * 指定当前期
     * @param lotteryType 彩种
     * @param phase 彩期
     */
    @RequestMapping(value = "/specifyCurrentPhase", method = RequestMethod.POST)
    public NumberLotteryData specifyCurrentPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) {
        logger.info("开始指定彩种[{}]当前期为[{}]", lotteryType.getName(), phase);
        return numberLotteryDataInternalService.specifyCurrentPhase(lotteryType, phase);
    }

    /**
     * 更新彩期开奖结果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param result 开奖结果
     */
    @RequestMapping(value = "/updateResult", method = RequestMethod.POST)
    public void updateResult(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("result") String result) {
        logger.info("开始更新彩种[{}]彩期[{}]开奖结果", lotteryType.getName(), phase);
        numberLotteryDataInternalService.updateResult(lotteryType, phase, result);
    }

    /**
     * 获取当前彩期前后n期彩期列表
     * @param lotteryType 彩种
     * @param prePhases 前n期
     * @param nextPhases 后n期
     * @return 当前彩期前后n期数字彩彩果列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findNearestPhaseList", method = RequestMethod.GET)
    public List<NumberLotteryData> findNearestPhaseList(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("prePhases") int prePhases, @RequestParam("nextPhases") int nextPhases) {
        logger.info("开始获取当前彩期前后n期彩期列表");
        return numberLotteryDataInternalService.findNearestPhaseList(lotteryType, prePhases, nextPhases);
    }

    /**
     * 获取指定彩期之前n期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param n 前n期
     * @return 指定彩期之前n期数字彩彩果列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findPreviousPhaseList", method = RequestMethod.GET)
    public List<NumberLotteryData> findPreviousPhaseList(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("n") int n) {
        logger.info("开始获取指定彩期之前n期");
        return numberLotteryDataInternalService.findPreviousPhaseList(lotteryType, phase, n);
    }

    /**
     * 获取指定彩期之后n期
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param n 后n期
     * @return 指定彩期之后n期数字彩彩果列表
     * @throws NumberLotteryDataException
     */
    @RequestMapping(value = "/findNextPhaseList", method = RequestMethod.GET)
    public List<NumberLotteryData> findNextPhaseList(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("n") int n) {
        logger.info("开始获取指定彩期之后n期");
        return numberLotteryDataInternalService.findNextPhaseList(lotteryType, phase, n);
    }

    /**
     * 更新数字彩彩果详情
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param poolAmount 奖池金额
     * @param saleAmount 销售金额
     * @param list 详情列表
     * @return 跟新后的数字彩彩果
     */
    @RequestMapping(value = "/updateDetailData", method = RequestMethod.POST)
    public void updateDetailData(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("poolAmount") Long poolAmount, @RequestParam("saleAmount") Long saleAmount, @RequestBody List<NumberLotteryDetailData> list) {
        logger.info("开始更新数字彩彩果详情");
        numberLotteryDataInternalService.updateDetailData(lotteryType, phase, poolAmount, saleAmount, list);
    }

    /**
     * 根据彩种彩期获取详情
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 获取到的详情列表
     */
    @RequestMapping(value = "/findByLotteryTypeAndPhase", method = RequestMethod.GET)
    NumberLotteryData findByLotteryTypeAndPhase(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase) {
        logger.info("根据彩种彩期获取详情");
        return numberLotteryDataInternalService.findByLotteryTypeAndPhase(lotteryType, phase);
    }

    /**
     * 根据数字彩彩果对象id获取详情信息列表
     * @param lotteryDataId 数字彩彩果对象id
     * @return 获取到的详情列表
     */
    @RequestMapping(value = "/findByLotteryDataId", method = RequestMethod.GET)
    List<NumberLotteryDetailData> findByLotteryDataId(@RequestParam("lotteryDataId") Long lotteryDataId) {
        logger.info("根据数字彩彩果对象id获取详情信息列表");
        return numberLotteryDetailDataInternalService.findByLotteryDataId(lotteryDataId);
    }
}
