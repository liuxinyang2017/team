package com.qatang.team.fetcher.bean;

import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;

import java.time.LocalDateTime;

/**
 * 代理验证日志
 * @author qatang
 */
public class ProxyValidateLog extends AbstractBaseApiBean {
    private static final long serialVersionUID = 2771227063335205275L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 代理编码
     */
    private Long proxyId;

    /**
     * 代理地址
     */
    private String host;

    /**
     * 代理端口
     */
    private int port;

    /**
     * 代理验证器类型
     */
    private ProxyValidatorType proxyValidatorType;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 开始测试时间
     */
    private LocalDateTime beginTestTime;

    /**
     * 结束测试时间
     */
    private LocalDateTime endTestTime;

    /**
     * 耗时，单位：毫秒
     */
    private Long spentMills;

    /**
     * 是否成功
     */
    private YesNoStatus success;

    /**
     * 响应信息
     */
    private String message;
}
