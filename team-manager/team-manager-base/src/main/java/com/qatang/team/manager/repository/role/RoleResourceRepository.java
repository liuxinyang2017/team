package com.qatang.team.manager.repository.role;


import com.qatang.team.manager.core.repository.IRepository;
import com.qatang.team.manager.entity.role.RoleResourceEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface RoleResourceRepository extends IRepository<RoleResourceEntity, Long> {

    List<RoleResourceEntity> findByRoleIdIn(List<Long> roleIdList);
}
