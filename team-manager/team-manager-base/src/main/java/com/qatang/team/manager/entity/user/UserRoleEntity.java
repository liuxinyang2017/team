package com.qatang.team.manager.entity.user;

import com.qatang.team.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author jinsheng
 * @since 2016-04-28 15:28
 */
@Entity
@Table(name = "a_user_role")
@DynamicInsert
@DynamicUpdate
public class UserRoleEntity extends AbstractEntity {

    private static final long serialVersionUID = 4280524573565391061L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
