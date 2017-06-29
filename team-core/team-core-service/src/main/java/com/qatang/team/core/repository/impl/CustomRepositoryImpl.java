package com.qatang.team.core.repository.impl;

import com.google.common.collect.Lists;
import com.qatang.team.core.repository.CustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author qatang
 */
public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {
    private final EntityManager entityManager;

    private final Class<T> domainClass;

    public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
        this.domainClass = domainClass;
    }

    @Override
    public T findOneForUpdate(ID id) {
        return entityManager.find(this.getDomainClass(), id, LockModeType.PESSIMISTIC_WRITE);
    }

    protected Page<T> readPageWithoutCountQuery(TypedQuery<T> query, Pageable pageable, Specification<T> spec) {
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<T> content = query.getResultList();

        return new PageImpl<T>(content, pageable, content.size());
    }

    @Override
    public Page<T> findAllWithoutCountQuery(Specification<T> spec, Pageable pageable) {
        TypedQuery<T> query = getQuery(spec, pageable);
        return pageable == null ? new PageImpl<>(query.getResultList()) : readPageWithoutCountQuery(query, pageable, spec);
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public <S extends T> void detach(S s) {
        entityManager.detach(s);
    }

    @Override
    public <S extends T> void detach(Iterable<S> iterable) {
        for (S s : iterable) {
            detach(s);
        }
    }

    @Override
    public BigDecimal sum(Specification<T> spec, String field) {
        return this.multiSum(spec, field).get(0);
    }

    @Override
    public List<BigDecimal> multiSum(Specification<T> spec, String... fields) {
        return multiSum(spec, Lists.newArrayList(fields));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BigDecimal> multiSum(Specification<T> spec, List<String> fieldList) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery query = builder.createQuery();

        Root<T> root = query.from(getDomainClass());

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);

            if (predicate != null) {
                query.where(predicate);
            }
        }

        List<Selection<?>> sumSelectList = Lists.newArrayList();
        for (String field : fieldList) {
            sumSelectList.add(builder.sum(root.get(field)));
        }
        query.multiselect(sumSelectList);

        List<BigDecimal> resultList = Lists.newArrayList();

        if (sumSelectList.size() == 1) {
            Object queryResult = entityManager.createQuery(query).getSingleResult();
            resultList.add(queryResult == null ? null : new BigDecimal(queryResult.toString()));
        } else {
            Object[] queryResult = (Object[]) entityManager.createQuery(query).getSingleResult();
            for (Object object : queryResult) {
                resultList.add(object == null ? null : new BigDecimal(object.toString()));
            }
        }

        return resultList;
    }
}
