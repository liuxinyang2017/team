package com.qatang.team.core.request;

import com.qatang.team.enums.common.PageOrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页请求
 * 支持排序
 * Created by sunshow on 5/19/15.
 */
public class ApiRequestPage implements Serializable {

    private static final long serialVersionUID = 5419230473169337073L;

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<ApiRequestOrder> orderList;

    private int page = 0;
    private int pageSize = 10;

    private boolean withoutCountQuery = false;

    private ApiRequestPage() {

    }

    public static ApiRequestPage newInstance() {
        return new ApiRequestPage();
    }

    public ApiRequestPage addOrder(String field) {
        return this.addOrder(field, PageOrderType.ASC);
    }

    public ApiRequestPage addOrder(String field, PageOrderType orderType) {
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        this.orderList.add(new ApiRequestOrder(field, orderType));
        return this;
    }

    public ApiRequestPage paging(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        return this;
    }

    public ApiRequestPage withoutCountQuery(boolean withoutCountQuery) {
        this.withoutCountQuery = withoutCountQuery;
        return this;
    }

    /**
     * 下一页继续查找
     */
    public ApiRequestPage pagingNext() {
        this.page ++;
        return this;
    }

    public List<ApiRequestOrder> getOrderList() {
        return orderList;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isWithoutCountQuery() {
        return withoutCountQuery;
    }
}

