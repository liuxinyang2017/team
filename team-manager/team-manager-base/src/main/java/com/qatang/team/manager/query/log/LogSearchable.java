package com.qatang.team.manager.query.log;

import com.google.common.collect.Lists;
import com.qatang.team.manager.core.query.AbstractSearchable;
import com.qatang.team.manager.entity.log.LogEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-05-13 14:38
 */
public class LogSearchable extends AbstractSearchable<LogEntity> {

    private static final long serialVersionUID = -5223857363257840153L;

    @Override
    protected Predicate createPredicate(Root<LogEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = Lists.newArrayList();

        if (StringUtils.isNotEmpty(this.getContent())) {
            predicateList.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("userId"), this.getContent()),
                    criteriaBuilder.like(root.get("url"), "%" + this.getContent() + "%"),
                    criteriaBuilder.like(root.get("params"), "%" + this.getContent() + "%")
            ));
        }

        if (this.getBeginCreatedTime() != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), this.getBeginCreatedTime()));
        }
        if (this.getEndCreatedTime() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdTime"), this.getEndCreatedTime()));
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        return null;
    }
}
