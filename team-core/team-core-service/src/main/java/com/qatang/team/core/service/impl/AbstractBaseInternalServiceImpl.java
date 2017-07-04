package com.qatang.team.core.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.qatang.team.core.annotation.request.RequestApiFieldUpdatable;
import com.qatang.team.core.bean.BaseApiBean;
import com.qatang.team.core.entity.BaseEntity;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestOrder;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.core.util.BeanMapping;
import com.qatang.team.core.util.CoreReflectionUtils;
import com.qatang.team.enums.common.OperatorType;
import com.qatang.team.enums.common.PageOrderType;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 预留基础服务实现, 为所有服务实现的父类
 * Created by sunshow on 4/27/15.
 */
public abstract class AbstractBaseInternalServiceImpl implements BaseInternalSerivce {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Sort convertSort(ApiRequestPage requestPage) {
        if (requestPage.getOrderList() != null && !requestPage.getOrderList().isEmpty()) {
            List<Sort.Order> orderList = new ArrayList<>();
            for (ApiRequestOrder requestOrder : requestPage.getOrderList()) {
                orderList.add(this.convertSortOrder(requestOrder));
            }
            return new Sort(orderList);
        }
        return null;
    }

    private Sort.Order convertSortOrder(ApiRequestOrder requestOrder) {
        Sort.Direction direction;
        if (requestOrder.getOrderType().equals(PageOrderType.DESC)) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        return new Sort.Order(direction, requestOrder.getField());
    }

    protected Pageable convertPageable(ApiRequestPage requestPage) {
        return new PageRequest(requestPage.getPage(), requestPage.getPageSize(), this.convertSort(requestPage));
    }

    private <T> Predicate convertPredicate(ApiRequestFilter filter, Root<T> root, CriteriaBuilder cb) {
        switch (filter.getOperatorType()) {
            case EQ:
                return cb.equal(root.get(filter.getField()), filter.getValue());
            case GE:
                if (filter.getValue() instanceof Comparable) {
                    return cb.greaterThanOrEqualTo(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case LE:
                if (filter.getValue() instanceof Comparable) {
                    return cb.lessThanOrEqualTo(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case GT:
                if (filter.getValue() instanceof Comparable) {
                    return cb.greaterThan(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case LT:
                if (filter.getValue() instanceof Comparable) {
                    return cb.lessThan(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case BETWEEN:
                Object val1 = filter.getValueList().get(0);
                Object val2 = filter.getValueList().get(1);
                if (val1 instanceof Comparable && val2 instanceof Comparable) {
                    return cb.between(root.get(filter.getField()), (Comparable) val1, (Comparable) val2);
                } else {
                    logger.error("字段({})不是可比较对象, value1={}, value2={}", filter.getField(), val1, val2);
                    return null;
                }
            case IN:
                return root.get(filter.getField()).in(filter.getValueList());
            case LIKE:
                return cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
            case NOT_NULL:
                return cb.isNotNull(root.get(filter.getField()));
            case NOT_EQUAL:
                return cb.notEqual(root.get(filter.getField()), filter.getValue());
            case AND:
            case OR:
                if (filter.getValueList() == null || filter.getValueList().isEmpty()) {
                    return null;
                }
                if (StringUtils.isNotBlank(filter.getField())) {
                    logger.error("OR 和 AND 操作不允许指定 field: {}", filter.getField());
                    return null;
                }
                List<Predicate> predicateList = Lists.newArrayList();
                for (Object o : filter.getValueList()) {
                    ApiRequestFilter f = (ApiRequestFilter) o;
                    // 递归调用
                    Predicate predicate = convertPredicate(f, root, cb);
                    if (predicate != null) {
                        predicateList.add(predicate);
                    }
                }
                if (!predicateList.isEmpty()) {
                    if (filter.getOperatorType() == OperatorType.AND) {
                        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
                    }
                    if (filter.getOperatorType() == OperatorType.OR) {
                        return cb.or(predicateList.toArray(new Predicate[predicateList.size()]));
                    }
                }
                return null;
            default:
                logger.error("不支持的运算符, op={}", filter.getOperatorType());
                return null;
        }
    }

    protected <T> Specification<T> convertSpecification(ApiRequest request) {
        if (request == null) {
            return null;
        }
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getFilterList() != null && !request.getFilterList().isEmpty()) {
                for (ApiRequestFilter filter : request.getFilterList()) {
                    Predicate predicate = convertPredicate(filter, root, cb);
                    if (predicate != null) {
                        predicateList.add(predicate);
                    }
                }
            }
            query.distinct(request.isDistinct());
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }

    protected <T, E> ApiResponse<E> convertApiResponse(Page<T> page, Class<E> c) {
        ApiResponse<E> apiResponse = new ApiResponse<>();
        apiResponse.setPage(page.getNumber());
        apiResponse.setPageSize(page.getSize());
        apiResponse.setTotal(page.getTotalElements());
        apiResponse.setPagedData(BeanMapping.mapList(page.getContent(), c));

        return apiResponse;
    }

    private static LoadingCache<Class<? extends BaseApiBean>, Set<String>> apiBeanUpdatableFieldCache = CacheBuilder.newBuilder().build(new CacheLoader<Class<? extends BaseApiBean>, Set<String>>() {
        @Override
        public Set<String> load(Class<? extends BaseApiBean> c) throws Exception {
            return CoreReflectionUtils.getFieldsAnnotatedWith(c, RequestApiFieldUpdatable.class).keySet().stream().map(Field::getName).collect(Collectors.toSet());
        }
    });

    private <T extends BaseApiBean> boolean isUpdatableField(Class<T> c, String fieldName) {
        try {
            return apiBeanUpdatableFieldCache.get(c).contains(fieldName);
        } catch (Exception e) {
            logger.error("从缓存中载入可更新字段集合出错", e);
            logger.error("要判断的类为: {}, 字段名为: {}", c.getName(), fieldName);
            return false;
        }
    }

    protected <E extends BaseEntity, S extends BaseApiBean> E copyUpdatableField(E entity, S source) {
        return copyUpdatableField(entity, source, false);
    }

    protected <E extends BaseEntity, S extends BaseApiBean> E copyUpdatableField(E entity, S source, boolean nullableUpdate) {
        Class sourceClass = source.getClass();
        while (sourceClass != null) {
            if (!BaseApiBean.class.isAssignableFrom(sourceClass)) {
                break;
            }
            try {
                for (Field field : sourceClass.getDeclaredFields()) {
                    String fieldName = field.getName();
                    if (!isUpdatableField(sourceClass, fieldName)) {
                        logger.info("字段({})不允许更新, 直接跳过, class={}", fieldName, sourceClass.getName());
                        continue;
                    }

                    Object fieldValue = PropertyUtils.getProperty(source, fieldName);
                    if (fieldValue == null && !nullableUpdate) {
                        continue;
                    }

                    BeanUtils.setProperty(entity, fieldName, fieldValue);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(String.format("类属性拷贝错误, message=%s, class=%s", e.getMessage(), sourceClass));
            }
            sourceClass = sourceClass.getSuperclass();
        }

        return entity;
    }
}
