package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.exception.FetcherLogException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wp
 * @since 2017/7/20
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/fetcherLog")
public interface FetcherLogApiService {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    FetcherLog create(@RequestBody FetcherLog fetcherLog) throws FetcherLogException;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody FetcherLog fetcherLog) throws FetcherLogException;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    FetcherLog update(@RequestBody FetcherLog fetcherLog) throws FetcherLogException;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    FetcherLog get(@RequestParam(name = "id") Long id) throws FetcherLogException;

}
