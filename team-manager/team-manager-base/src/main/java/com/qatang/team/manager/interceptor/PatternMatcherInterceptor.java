package com.qatang.team.manager.interceptor;


import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.manager.entity.resource.ResourceEntity;
import com.qatang.team.manager.service.resource.ResourceService;
import com.qatang.team.manager.service.role.RoleService;
import com.qatang.team.manager.service.user.UserService;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-05-18 10:28
 */
@Component
public class PatternMatcherInterceptor extends HandlerInterceptorAdapter implements ServletContextAware {

    private PatternMatcher matcher = new AntPathMatcher();

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean match = false;

        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        if (userInfo == null) {
            return false;
        }

        String uri = request.getRequestURI().substring(request.getContextPath().length());

        List<Long> roleIdList = userService.findRoleIdByUserId(userInfo.getId());
        List<Long> resourceIdList = roleService.findResourceIdByRoleIdIn(roleIdList);
        List<ResourceEntity> resourceEntityList = resourceService.findAll(resourceIdList);

        if (resourceEntityList != null && !resourceEntityList.isEmpty()) {
            for (ResourceEntity resourceEntity : resourceEntityList) {
                if (Objects.equals(resourceEntity.getValid(), EnableDisableStatus.ENABLE)) {
                    if (StringUtils.isNotEmpty(resourceEntity.getUrl()) && matcher.matches(resourceEntity.getUrl(), uri)) {
                        match = true;
                        break;
                    }
                }
            }
        }

        if (!match) {
            response.sendRedirect("/unauthorized");
        }

        return match;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
