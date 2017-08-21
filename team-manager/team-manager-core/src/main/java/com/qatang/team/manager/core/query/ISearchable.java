package com.qatang.team.manager.core.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

/**
 * @author jinsheng
 * @since 2016-04-28 14:10
 */
public interface ISearchable<T> extends Serializable {

    Pageable getPageable();

    Sort getSortable();

    Specification<T> getSpecification();
}
