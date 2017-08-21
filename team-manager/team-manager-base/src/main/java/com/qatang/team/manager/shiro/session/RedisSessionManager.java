package com.qatang.team.manager.shiro.session;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

import static com.zhangyu.arsenal.core.constants.GlobalConstants.USER_SESSION_ID;

/**
 * @author jinsheng
 * @since 2016-05-03 16:41
 */
public class RedisSessionManager extends ServletContainerSessionManager {

    public RedisSessionManager() {

    }

    @Override
    public Session getSession(SessionKey key) throws SessionException {
        if (!WebUtils.isHttp(key)) {
            String msg = "SessionKey must be an HTTP compatible implementation.";
            throw new IllegalArgumentException(msg);
        }

        HttpServletRequest request = WebUtils.getHttpRequest(key);

        Session session = null;

        HttpSession httpSession = request.getSession();

        if (Objects.nonNull(httpSession) && Objects.nonNull(httpSession.getAttribute(USER_SESSION_ID))) {
            session = createSession(httpSession, request.getRemoteHost());
        }

        return session;
    }

    protected Session createSession(SessionContext sessionContext) throws AuthorizationException {

        if (!WebUtils.isHttp(sessionContext)) {
            String msg = "SessionContext must be an HTTP compatible implementation.";
            throw new IllegalArgumentException(msg);
        }

        HttpServletRequest request = WebUtils.getHttpRequest(sessionContext);
        HttpSession httpSession = request.getSession();

        String userSessionId = Objects.isNull(sessionContext.getSessionId()) ? UUID.randomUUID().toString() : sessionContext.getSessionId().toString();
        httpSession.setAttribute(USER_SESSION_ID, userSessionId);

        String host = getHost(sessionContext);
        return createSession(httpSession, host);
    }

    private String getHost(SessionContext context) {
        String host = context.getHost();
        if (host == null) {
            ServletRequest request = WebUtils.getRequest(context);
            if (request != null) {
                host = request.getRemoteHost();
            }
        }
        return host;
    }
}
