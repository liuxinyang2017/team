package com.qatang.team.gateway.filter.pre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证访问请求方式 请求头等
 * @author wangzhiliang
 */
@Component
public class RequestFilter extends ZuulFilter {
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run(){
        long startTime = System.currentTimeMillis();

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

            if(ctx.debugRequest()) {

                System.out.println("debug");
            }
/*
        //验证请求方法
        if(!request.getMethod().equals(HttpMethod.POST.name())) {
            //ctx.setSendZuulResponse(false);
            throw new RequirePostMethodException();
        }
        logger.error(request.getPathInfo());*/
        doSomething();

/*        //验证请求头
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (!StringUtils.contains(contentType, "application/json")) {
            ctx.setSendZuulResponse(false);
            throw new InvalidRequestHeaderException();
        }*/

        return null;
    }

    private void doSomething() {
        //logger.info("我被执行了");
        throw new RuntimeException("some error");
    }
}
