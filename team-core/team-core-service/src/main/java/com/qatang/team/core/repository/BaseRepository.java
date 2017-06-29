package com.qatang.team.core.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author qatang
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends CustomRepository<T, ID>, JpaSpecificationExecutor<T> {
}
