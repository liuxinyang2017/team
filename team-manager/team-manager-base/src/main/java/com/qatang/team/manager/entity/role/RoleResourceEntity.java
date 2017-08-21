package com.qatang.team.manager.entity.role;

import com.qatang.team.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author jinsheng
 * @since 2016-04-28 15:28
 */
@Entity
@Table(name = "a_role_resource")
@DynamicInsert
@DynamicUpdate
public class RoleResourceEntity extends AbstractEntity {

    private static final long serialVersionUID = 3802004824464339689L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
