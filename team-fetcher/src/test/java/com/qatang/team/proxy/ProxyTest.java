package com.qatang.team.proxy;

import org.junit.Test;

/**
 * @author qatang
 */
public class ProxyTest {
    @Test
    public void testProxy() {
//        ProxyManager.update();

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        OkHttpClient httpClient = builder
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://httpbin.org/get")
//                .build();
//
//        try (Response response = httpClient.newCall(request).execute()) {
//            try (ResponseBody responseBody = response.body()) {
//
//                System.out.println(responseBody.string());
//            }
//        } catch (Exception e) {
//
//        }

//        ApiRetryTaskExecutor.invoke(new ApiRetryCallback<Void>() {
//
//            @Override
//            protected Void execute() throws Exception {
//                INumberLotteryFetcher numberLotteryFetcher = new SsqOfficialFetcher();
//                NumberLotteryFetchResult numberLotteryFetchResult = numberLotteryFetcher.fetchResult("2017077");
//                Assert.assertTrue(numberLotteryFetchResult.getResult() != null);
//                System.out.println(numberLotteryFetchResult.getResult());
//                return null;
//            }
//        });
//
//        ApiRetryTaskExecutor.invoke(new ApiRetryCallback<Void>() {
//
//            @Override
//            protected Void execute() throws Exception {
//                INumberLotteryFetcher numberLotteryFetcher = new SsqOfficialFetcher();
//                NumberLotteryFetchResult numberLotteryFetchResult = numberLotteryFetcher.fetchDetail("2017077");
//                Assert.assertTrue(numberLotteryFetchResult.getResult() != null);
//                System.out.println(numberLotteryFetchResult.getResult());
//                System.out.println(numberLotteryFetchResult.getSaleAmount());
//                System.out.println(numberLotteryFetchResult.getPoolAmount());
//                numberLotteryFetchResult.getDetailList().forEach(numberLotteryFetchDetail -> {
//                    System.out.println(numberLotteryFetchDetail.getPrizeKey());
//                    System.out.println(numberLotteryFetchDetail.getPrizeName());
//                    System.out.println(numberLotteryFetchDetail.getPrizeCount());
//                    System.out.println(numberLotteryFetchDetail.getPrizeAmount());
//                });
//                return null;
//            }
//        });


    }
}
