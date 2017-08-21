package com.qatang.team.manager.form.role;


import com.qatang.team.manager.core.form.AbstractForm;
import com.qatang.team.manager.entity.role.RoleEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class RoleForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private RoleEntity role;

    private List<Long> resourceIdList;

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public List<Long> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Long> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }
}
