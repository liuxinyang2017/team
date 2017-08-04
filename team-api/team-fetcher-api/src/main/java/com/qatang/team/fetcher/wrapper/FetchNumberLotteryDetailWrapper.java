package com.qatang.team.fetcher.wrapper;

import com.qatang.team.core.wrapper.BaseWrapper;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;

import java.util.List;

/**
 * @author wp
 * @since 2017/8/4
 */
public class FetchNumberLotteryDetailWrapper implements BaseWrapper {
    private static final long serialVersionUID = -7599447512516869107L;

    private FetchNumberLotteryDetailData fetchNumberLotteryDetailData;

    private List<FetchNumberLotteryDetailItemData> fetchNumberLotteryDetailItemDataList;

    public FetchNumberLotteryDetailData getFetchNumberLotteryDetailData() {
        return fetchNumberLotteryDetailData;
    }

    public void setFetchNumberLotteryDetailData(FetchNumberLotteryDetailData fetchNumberLotteryDetailData) {
        this.fetchNumberLotteryDetailData = fetchNumberLotteryDetailData;
    }

    public List<FetchNumberLotteryDetailItemData> getFetchNumberLotteryDetailItemDataList() {
        return fetchNumberLotteryDetailItemDataList;
    }

    public void setFetchNumberLotteryDetailItemDataList(List<FetchNumberLotteryDetailItemData> fetchNumberLotteryDetailItemDataList) {
        this.fetchNumberLotteryDetailItemDataList = fetchNumberLotteryDetailItemDataList;
    }
}
