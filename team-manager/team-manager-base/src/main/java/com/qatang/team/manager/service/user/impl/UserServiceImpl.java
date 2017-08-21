package com.qatang.team.manager.service.user.impl;

import ch.qos.logback.core.util.SystemInfo;
import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.common.OperatorType;
import com.qatang.team.manager.entity.user.UserRoleEntity;
import com.qatang.team.manager.query.user.UserSearchable;
import com.qatang.team.manager.repository.user.UserRoleRepository;
import com.qatang.team.manager.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jinsheng
 * @since 2016-04-27 15:57
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoApiService userInfoApiService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SystemInfoApiService systemInfoApiService;

    @Autowired
    private UserSystemApiService userSystemApiService;

    @Override
    public List<Long> findRoleIdByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId).stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
    }

    @Override
    public ApiResponse<UserInfo> findAll(UserSearchable userSearchable, Pageable pageable, String identifier) {
        ApiRequest request = ApiRequest.newInstance();
        SystemInfo systemInfo = systemInfoApiService.findByIdentifier(identifier);
        if (systemInfo == null) {
            return new ApiResponse<>(pageable.getPageNumber(), pageable.getPageSize(), Lists.newArrayList(), 0);
        }

        List<Long> userIdList = userSystemApiService.findUserIdBySystemId(systemInfo.getId());
        if (userIdList == null || userIdList.isEmpty()) {
            return new ApiResponse<>(pageable.getPageNumber(), pageable.getPageSize(), Lists.newArrayList(), 0);
        }
        request.filterIn(QUserInfo.id, userIdList);

        if (StringUtils.isNotEmpty(userSearchable.getId())) {
            String[] ids = StringUtils.split(userSearchable.getId(), ",");
            if (ids != null && ids.length > 0) {
                List<Long> idList = Lists.newArrayList();
                Lists.newArrayList(ids).forEach(id -> idList.add(Long.valueOf(id)));
                request.filterIn(QUserInfo.id, idList);
            }
        }

        if (StringUtils.isNotEmpty(userSearchable.getContent())) {
            request.filterOr(
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.username, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.name, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.email, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.mobile, userSearchable.getContent())
            );
        }

        if (userSearchable.getBeginCreatedTime() != null) {
            request.filterGreaterEqual(QUserInfo.createdTime, userSearchable.getBeginCreatedTime());
        }

        if (userSearchable.getEndCreatedTime() != null) {
            request.filterLessEqual(QUserInfo.createdTime, userSearchable.getEndCreatedTime());
        }

        if (userSearchable.getValid() != null && !Objects.equals(EnableDisableStatus.ALL, userSearchable.getValid())) {
            request.filterEqual(QUserInfo.valid, userSearchable.getValid());
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        userSearchable.convertPageable(requestPage, pageable);

        return userInfoApiService.findAll(request, requestPage);
    }

    @Override
    public void bindRole(Long userId, List<Long> roleIdList) {

        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userId);

        if (userRoleEntityList != null && !userRoleEntityList.isEmpty()) {
            userRoleRepository.deleteInBatch(userRoleEntityList);
        }

        if (roleIdList != null && !roleIdList.isEmpty()) {
            List<UserRoleEntity> saveUserRoleEntityList = Lists.newArrayList();
            roleIdList.forEach(roleId -> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUserId(userId);
                userRoleEntity.setRoleId(roleId);
                saveUserRoleEntityList.add(userRoleEntity);
            });
            userRoleRepository.save(saveUserRoleEntityList);
        }
    }
}
