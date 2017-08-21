package com.qatang.team.manager.core.constants;

/**
 * @author jinsheng
 * @since 2016-04-28 14:25
 */
public class GlobalConstants {

    /**
     * 默认每页记录条数
     */
    public static final int DEFAULT_PAGE_SIZE = 50;

    /**
     * 最大非活动时间(秒)
     */
    public static final int MAX_INACTIVE_INTERVAL_IN_SECONDS = 7200;

    /**
     * session中保存用户信息的id
     */
    public static final String USER_SESSION_ID = "USER_SESSION_ID";

    /**
     * 用户中心系统唯一标识
     */
    public static final String SYSTEM_ARSENAL_IDENTIFIER = "system:zy:team";

    /**
     * 自定义cookieName
     */
    public static final String CUSTOM_COOKIE_NAME = "TEAM_MANAGER_SESSION";

    /**
     * 自定义cookiePath
     */
    public static final String CUSTOM_COOKIE_PATH = "/";

    /**
     * 域名格式
     */
    public static final String DOMAIN_NAME_PATTERN = "^.+?\\.(\\w+\\.[a-z]+)$";

    /**
     * 默认资源级数
     */
    public static final int DEFAULT_TREE_LEVEL = 3;
}
