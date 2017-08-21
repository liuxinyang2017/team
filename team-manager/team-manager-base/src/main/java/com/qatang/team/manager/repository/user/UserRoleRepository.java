package com.qatang.team.manager.repository.user;


import com.qatang.team.manager.core.repository.IRepository;
import com.qatang.team.manager.entity.user.UserRoleEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface UserRoleRepository extends IRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findByUserId(Long userId);
}
