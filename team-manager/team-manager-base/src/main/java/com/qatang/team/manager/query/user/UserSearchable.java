package com.qatang.team.manager.query.user;


import com.qatang.team.manager.core.query.CommonSearchable;

/**
 * @author jinsheng
 * @since 2016-05-09 10:55
 */
public class UserSearchable extends CommonSearchable {

    private static final long serialVersionUID = -2048350258192544932L;

    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
