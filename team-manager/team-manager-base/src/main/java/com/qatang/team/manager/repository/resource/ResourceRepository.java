package com.qatang.team.manager.repository.resource;


import com.qatang.team.manager.core.repository.IRepository;
import com.qatang.team.manager.entity.resource.ResourceEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface ResourceRepository extends IRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByParentId(Long parentId);
}
