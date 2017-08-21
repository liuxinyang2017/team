package com.qatang.team.manager.service.resource.impl;


import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.manager.core.service.AbstractService;
import com.qatang.team.manager.entity.resource.ResourceEntity;
import com.qatang.team.manager.repository.resource.ResourceRepository;
import com.qatang.team.manager.service.resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-28 16:09
 */
@Service
@Transactional
public class ResourceServiceImpl extends AbstractService<ResourceEntity, Long> implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<ResourceEntity> findByParentId(Long parentId) {
        return resourceRepository.findByParentId(parentId);
    }

    @Override
    public void updateValid(Long id, EnableDisableStatus toStatus) {
        ResourceEntity resourceEntity = resourceRepository.findOne(id);
        if (Objects.equals(resourceEntity.getValid(), toStatus)) {
            return;
        }
        resourceEntity.setValid(toStatus);
        resourceRepository.saveAndFlush(resourceEntity);
    }
}
