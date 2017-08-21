package com.qatang.team.manager.entity.log;


import com.qatang.team.manager.core.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author jinsheng
 * @since 2016-05-13 14:18
 */
@Entity
@Table(name = "a_log")
public class LogEntity extends AbstractEntity {

    private static final long serialVersionUID = 1195039157803982555L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String url;

    private String params;

    @Column(name = "remote_ip")
    private String remoteIp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
