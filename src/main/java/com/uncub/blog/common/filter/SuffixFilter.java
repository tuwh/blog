package com.uncub.blog.common.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * shiro过滤器，过滤指定的请求
 */
@Deprecated
public class SuffixFilter extends PathMatchingFilter {

    private final String DEFAULT_PASS_SUFFIX = ".css,.js,.html,.png,.jpg,.htm";
    private String passSuffix;


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        if (!(request instanceof HttpServletRequest)){
            return false;
        }
        else {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            httpServletRequest.getContextPath();
            return false;
        }
    }

    public String getPassSuffix() {
        return passSuffix;
    }

    public void setPassSuffix(String passSuffix) {
        this.passSuffix = passSuffix;
    }
}
