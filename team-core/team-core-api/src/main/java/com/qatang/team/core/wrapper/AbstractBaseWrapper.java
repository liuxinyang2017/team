package com.qatang.team.core.wrapper;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestOrder;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.enums.common.PageOrderType;

import java.util.List;

/**
 * @author wangzhiliang
 */
public abstract class AbstractBaseWrapper implements BaseWrapper {
    private static final long serialVersionUID = -6242459516641336337L;

    private int pageSize = 50; // 每页显示记录数
    private int page = 1; // 当前页数

    private List<ApiRequestOrder> orderList;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ApiRequestOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ApiRequestOrder> orderList) {
        this.orderList = orderList;
    }

    public ApiRequestPage convertPageable() {
        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(this.getPage(), this.getPageSize());

        if (orderList != null) {
            orderList.forEach(order -> {
                PageOrderType pageOrderType = order.getOrderType();
                requestPage.addOrder(order.getField(), pageOrderType);
            });
        }
        return requestPage;
    }

    public abstract ApiRequest convertRequest();
}
