package com.qatang.team.core.component.request;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * author: sunshow.
 */
public class ApiPageRequestHelper {

    public static <T> List<T> request(ApiRequest apiRequest, ApiRequestPage apiRequestPage, BiFunction<ApiRequest, ApiRequestPage, ApiResponse<T>> biFunction) throws RuntimeException {
        return request(apiRequest, apiRequestPage, biFunction, 0);
    }

    public static <T> List<T> request(ApiRequest apiRequest, ApiRequestPage apiRequestPage, BiFunction<ApiRequest, ApiRequestPage, ApiResponse<T>> biFunction, int maxPagingCount) throws RuntimeException {
        List<T> result = Lists.newArrayList();

        while (true) {
            ApiResponse<T> apiResponse = biFunction.apply(apiRequest, apiRequestPage);

            if (apiResponse == null || apiResponse.getCount() == 0) {
                break;
            }

            result.addAll(apiResponse.getPagedData());

            if (apiResponse.getCount() < apiRequestPage.getPageSize()) {
                break;
            }

            apiRequestPage.pagingNext();

            if (maxPagingCount > 0 && apiRequestPage.getPage() >= maxPagingCount) {
                // 达到最大翻页次数
                break;
            }
        }

        return result;
    }

    public static <T> List<T> request(PageableWrapper pageableWrapper, Function<PageableWrapper, ApiResponse<T>> function) throws RuntimeException {
        return request(pageableWrapper, function, 0);
    }

    public static <T> List<T> request(PageableWrapper pageableWrapper, Function<PageableWrapper, ApiResponse<T>> function, int maxPagingCount) throws RuntimeException {
        List<T> result = Lists.newArrayList();

        while (true) {
            ApiResponse<T> apiResponse = function.apply(pageableWrapper);

            if (apiResponse == null || apiResponse.getCount() == 0) {
                break;
            }

            result.addAll(apiResponse.getPagedData());

            if (apiResponse.getCount() < pageableWrapper.getRequestPage().getPageSize()) {
                break;
            }

            pageableWrapper.getRequestPage().pagingNext();

            if (maxPagingCount > 0 && pageableWrapper.getRequestPage().getPage() >= maxPagingCount) {
                // 达到最大翻页次数
                break;
            }
        }

        return result;
    }
}
