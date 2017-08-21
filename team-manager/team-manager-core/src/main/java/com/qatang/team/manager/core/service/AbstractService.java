package com.qatang.team.manager.core.service;

import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.manager.core.constants.GlobalConstants;
import com.qatang.team.manager.core.query.ISearchable;
import com.qatang.team.manager.core.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 14:15
 */
@Transactional
public abstract class AbstractService<T, ID extends Serializable> implements IService<T, ID> {

    @Autowired
    protected IRepository<T, ID> repository;

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public T get(ID id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Iterable<ID> iterable) {
        return repository.findAll(iterable);
    }

    @Override
    public Page<T> find(ISearchable searchable) {
        if (searchable == null) {
            return repository.findAll(new PageRequest(0, GlobalConstants.DEFAULT_PAGE_SIZE));
        }
        return repository.findAll(searchable.getSpecification(), searchable.getPageable());
    }

    @Override
    public long count(ISearchable searchable) {
        if (searchable == null) {
            return repository.count();
        }
        return repository.count(searchable.getSpecification());
    }

    @Override
    public void updateValid(ID id, EnableDisableStatus toStatus) {

    }
}
