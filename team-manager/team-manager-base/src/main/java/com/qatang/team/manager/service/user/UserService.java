package com.qatang.team.manager.service.user;


import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.manager.query.user.UserSearchable;
import com.rabbitmq.http.client.domain.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 15:54
 */
public interface UserService {

    List<Long> findRoleIdByUserId(Long userId);

    ApiResponse<UserInfo> findAll(UserSearchable userSearchable, Pageable pageable, String identifier);

    void bindRole(Long userId, List<Long> roleIdList);
}
