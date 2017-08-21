package com.qatang.team.manager.core.service;

import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.manager.core.query.ISearchable;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 14:02
 */
public interface IService<T, ID extends Serializable> {
    T save(T t);

    T update(T t);

    T get(ID id);

    void delete(ID id);

    List<T> findAll();

    List<T> findAll(Iterable<ID> iterable);

    /**
     * 根据条件查询所有
     * 条件 + 分页 + 排序
     * @param searchable
     * @return
     */
    Page<T> find(ISearchable searchable);

    /**
     * 根据条件统计所有记录数
     * @param searchable
     * @return
     */
    long count(ISearchable searchable);

    void updateValid(ID id, EnableDisableStatus toStatus);
}
