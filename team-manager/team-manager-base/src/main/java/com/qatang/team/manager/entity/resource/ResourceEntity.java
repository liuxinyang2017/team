package com.qatang.team.manager.entity.resource;


import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.converter.EnableDisableStatusConverter;
import com.qatang.team.manager.core.entity.AbstractEntity;
import jdk.management.resource.ResourceType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:58
 */
@Entity
@Table(name = "a_resource")
@DynamicInsert
@DynamicUpdate
public class ResourceEntity extends AbstractEntity {

    private static final long serialVersionUID = 8337067403282642537L;

    @Id
    @GeneratedValue
    private Long id;

    private String identifier;

    @Column(nullable = false)
    private String name;

    private String url;

    @Convert(converter = ResourceTypeConverter.class)
    @Column(name = "type", nullable = false)
    private ResourceType type;

    @Column(name = "tree_level", nullable = false)
    private Integer treeLevel = 0;

    @Column(nullable = false)
    private Integer priority = 0;

    @Column(name = "is_end", nullable = false)
    private boolean isEnd = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    private String memo;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "resource_icon")
    private String resourceIcon;

    @Transient
    private ResourceEntity parent;

    @Transient
    private List<ResourceEntity> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public ResourceEntity getParent() {
        return parent;
    }

    public void setParent(ResourceEntity parent) {
        this.parent = parent;
    }

    public List<ResourceEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceEntity> children) {
        this.children = children;
    }
}
