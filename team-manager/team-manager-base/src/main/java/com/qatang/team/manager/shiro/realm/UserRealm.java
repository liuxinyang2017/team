package com.qatang.team.manager.shiro.realm;

import com.google.common.collect.Sets;
import com.zhangyu.arsenal.manager.service.resource.ResourceService;
import com.zhangyu.arsenal.manager.service.role.RoleService;
import com.zhangyu.arsenal.manager.service.user.UserService;
import com.zhangyu.arsenal.manager.shiro.authentication.PasswordHelper;
import com.zhangyu.core.enums.EnableDisableStatus;
import com.zhangyu.ucenter.api.bean.system.SystemInfo;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import com.zhangyu.ucenter.api.exception.user.UserSystemException;
import com.zhangyu.ucenter.api.service.system.SystemInfoApiService;
import com.zhangyu.ucenter.api.service.user.UserInfoApiService;
import com.zhangyu.ucenter.api.service.user.UserSystemApiService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.zhangyu.arsenal.core.constants.GlobalConstants.SYSTEM_ARSENAL_IDENTIFIER;

/**
 * @author jinsheng
 * @since 2016-04-27 15:50
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoApiService userInfoApiService;

    @Autowired
    private SystemInfoApiService systemInfoApiService;

    @Autowired
    private UserSystemApiService userSystemApiService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = Sets.newHashSet();

        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        List<Long> roleIdList = userService.findRoleIdByUserId(userInfo.getId());
        List<Long> resourceIdList = roleService.findResourceIdByRoleIdIn(roleIdList);
        resourceService.findAll(resourceIdList).forEach(resourceEntity -> {
            if (Objects.nonNull(resourceEntity) && StringUtils.isNotEmpty(resourceEntity.getIdentifier())) {
                stringPermissions.add(resourceEntity.getIdentifier());
            }
        });

        authorizationInfo.setStringPermissions(stringPermissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        UserInfo userInfo = userInfoApiService.findByUsername(username);

        if (userInfo == null) {
            userInfo = userInfoApiService.findByEmail(username);
        }

        if (userInfo == null) {
            throw new UnknownAccountException("帐号或密码错误！");
        }

        SystemInfo systemInfo = systemInfoApiService.findByIdentifier(SYSTEM_ARSENAL_IDENTIFIER);

        if (Objects.isNull(systemInfo) || Objects.isNull(systemInfo.getId())) {
            throw new AuthenticationException("未查询到用户中心系统的记录");
        }

        try {
            userSystemApiService.allowAccess(userInfo.getId(), systemInfo.getId());
        } catch (UserSystemException e) {
            throw new AccountException(e.getMessage());
        }

        if (Objects.equals(userInfo.getValid(), EnableDisableStatus.DISABLE)) {
            throw new LockedAccountException("帐号无效！");
        }

        return new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(passwordHelper.getSalt(userInfo)),
                getName()
        );
    }
}
