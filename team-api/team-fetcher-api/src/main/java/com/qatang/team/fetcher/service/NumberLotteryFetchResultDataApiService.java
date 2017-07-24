package com.qatang.team.fetcher.service;

import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchResultDataException;
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
@RequestMapping(value = "/fetcher/numberLotteryFetchResultData")
public interface NumberLotteryFetchResultDataApiService {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryFetchResultData create(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryFetchResultData update(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryFetchResultData get(@RequestParam(name = "id") Long id) throws NumberLotteryFetchResultDataException;
}
