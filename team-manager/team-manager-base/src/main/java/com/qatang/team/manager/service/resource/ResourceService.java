package com.qatang.team.manager.service.resource;


import com.qatang.team.manager.core.service.IService;
import com.qatang.team.manager.entity.resource.ResourceEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 16:08
 */
public interface ResourceService extends IService<ResourceEntity, Long> {

    List<ResourceEntity> findByParentId(Long parentId);
}
