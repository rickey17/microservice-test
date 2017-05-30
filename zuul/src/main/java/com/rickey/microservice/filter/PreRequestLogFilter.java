package com.rickey.microservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rickey_17 on 17-5-28.
 */
public class PreRequestLogFilter extends ZuulFilter{

    private static final Logger logger = LoggerFactory.getLogger(PreRequestLogFilter.class);

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info(String.format("send %s request to %s",
                request.getMethod(),
                request.getRequestURL().toString()));
        return null;
    }
}
