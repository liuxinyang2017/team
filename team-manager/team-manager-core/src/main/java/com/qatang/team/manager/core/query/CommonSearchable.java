package com.qatang.team.manager.core.query;

import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.common.PageOrderType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-05-12 13:25
 */
public abstract class CommonSearchable implements Serializable {

    private String id;
    private String content;
    private Date beginCreatedTime;
    private Date endCreatedTime;
    private EnableDisableStatus valid;

    public void convertPageable(ApiRequestPage requestPage, Pageable pageable) {
        if (pageable != null) {
            requestPage.paging(pageable.getPageNumber(), pageable.getPageSize());

            if (pageable.getSort() != null) {
                pageable.getSort().forEach(order -> {
                    PageOrderType pageOrderType = Objects.equals(order.getDirection(), Sort.Direction.ASC) ? PageOrderType.ASC : PageOrderType.DESC;
                    requestPage.addOrder(order.getProperty(), pageOrderType);
                });
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginCreatedTime() {
        return beginCreatedTime;
    }

    public void setBeginCreatedTime(Date beginCreatedTime) {
        this.beginCreatedTime = beginCreatedTime;
    }

    public Date getEndCreatedTime() {
        return endCreatedTime;
    }

    public void setEndCreatedTime(Date endCreatedTime) {
        this.endCreatedTime = endCreatedTime;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }
}
