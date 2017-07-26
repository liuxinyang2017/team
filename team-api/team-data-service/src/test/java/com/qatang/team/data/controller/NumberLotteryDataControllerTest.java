package com.qatang.team.data.controller;

import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jinsheng
 */
public class NumberLotteryDataControllerTest extends AbstractControllerTest {
    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryData numberLotteryData = numberLotteryDataApiService.get(id);
    }
}
