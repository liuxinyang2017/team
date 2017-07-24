package com.qatang.team.fetcher.service;

import com.qatang.team.fetcher.bean.NumberLotteryFetchDetailData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchDetailDataException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wp
 * @since 2017/7/23
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/numberLotteryFetchDetailData")
public interface NumberLotteryFetchDetailDataApiService {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryFetchDetailData create(@RequestBody NumberLotteryFetchDetailData numberLotteryFetchDetailData) throws NumberLotteryFetchDetailDataException;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryFetchDetailData update(@RequestBody NumberLotteryFetchDetailData numberLotteryFetchDetailData) throws NumberLotteryFetchDetailDataException;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryFetchDetailData get(@RequestParam(name = "id") Long id) throws NumberLotteryFetchDetailDataException;
}
