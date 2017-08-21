package com.qatang.team.manager.core.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.manager.core.query.CommonSearchable;


/**
 * @author jinsheng
 * @since 2016-06-12 10:47
 */
public interface CommonService<T> {

    ApiResponse<T> findAll(CommonSearchable commonSearchable, PageableWrapper pageable);
}
