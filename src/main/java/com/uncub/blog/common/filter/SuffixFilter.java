package com.uncub.blog.common.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.http.HttpRequest;
import sun.dc.path.PathError;
import sun.dc.path.PathException;
import sun.dc.pr.PathFiller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;

/**
 * shiro过滤器，过滤指定的请求
 */
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
