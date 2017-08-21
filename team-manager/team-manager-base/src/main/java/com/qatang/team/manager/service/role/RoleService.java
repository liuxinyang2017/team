package com.qatang.team.manager.service.role;


import com.qatang.team.manager.core.service.IService;
import com.qatang.team.manager.entity.role.RoleEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface RoleService extends IService<RoleEntity, Long> {

    List<Long> findResourceIdByRoleIdIn(List<Long> roleIdList);

    void bindResource(Long roleId, List<Long> resourceIdList);
}
