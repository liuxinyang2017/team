package com.qatang.team.manager.form.resource;


import com.qatang.team.manager.core.form.AbstractForm;
import com.qatang.team.manager.entity.resource.ResourceEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 18:29
 */
public class ResourceForm extends AbstractForm {

    private static final long serialVersionUID = 123515295047880821L;

    private ResourceEntity resource;

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }
}
