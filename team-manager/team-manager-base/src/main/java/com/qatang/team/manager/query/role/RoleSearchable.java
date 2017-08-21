package com.qatang.team.manager.query.role;

import com.google.common.collect.Lists;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.manager.core.query.AbstractSearchable;
import com.qatang.team.manager.entity.role.RoleEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-05-12 13:15
 */
public class RoleSearchable extends AbstractSearchable<RoleEntity> {

    private static final long serialVersionUID = 4620624822480779847L;

    private YesNoStatus isDefault;

    @Override
    protected Predicate createPredicate(Root<RoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = Lists.newArrayList();

        if (StringUtils.isNotEmpty(this.getId())) {
            Path<String> idPath = root.get("id");
            List<String> userIdList = Arrays.asList(StringUtils.split(this.getId(), ","));
            predicateList.add(idPath.in(userIdList));
        }

        if (StringUtils.isNotEmpty(this.getContent())) {
            predicateList.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("identifier"), "%" + this.getContent() + "%"),
                    criteriaBuilder.like(root.get("name"), "%" + this.getContent() + "%"),
                    criteriaBuilder.like(root.get("description"), "%" + this.getContent() + "%")
            ));
        }

        if (this.getBeginCreatedTime() != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), this.getBeginCreatedTime()));
        }
        if (this.getEndCreatedTime() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdTime"), this.getEndCreatedTime()));
        }
        if (this.getValid() != null && !Objects.equals(this.getValid(), EnableDisableStatus.ALL)) {
            predicateList.add(criteriaBuilder.equal(root.get("valid"), this.getValid()));
        }
        if (this.getIsDefault() != null && !Objects.equals(this.getIsDefault(), YesNoStatus.ALL)) {
            predicateList.add(criteriaBuilder.equal(root.get("isDefault"), this.getIsDefault()));
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        return null;
    }

    public YesNoStatus getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(YesNoStatus isDefault) {
        this.isDefault = isDefault;
    }
}
