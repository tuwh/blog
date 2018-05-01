package com.uncub.blog.common.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 资源拦截器
 * 1、根据静态资源后缀名直接过滤，不走shiro拦截
 * 2、动态配置需要登陆验证的url
 * 3、本处配置优先级最高
 */
public class UncubPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    private static transient final Logger log = LoggerFactory.getLogger(PathMatchingFilterChainResolver.class);

    /**
     * 无需登陆的url后缀
     */
    private Set<String> unFilterSuffix;
    /**
     * 必须确认登陆的url
     */
    private Set<String> authFilterUrl;
    /**
     * 必须有用户的角色，可通过rememberMe登陆
     */
    private Set<String> userFilterUrl;

    /**
     * 无需登陆的url
     */
    private Set<String> unFilterUrl;


    {
        unFilterSuffix = new HashSet<>();
        unFilterSuffix.add(".html");
        unFilterSuffix.add(".htm");
        unFilterSuffix.add(".js");
        unFilterSuffix.add(".css");

        unFilterUrl = new HashSet<>();
        unFilterUrl.add("/user/login");
    }

    /**
     * @param request
     * @param response
     * @param originalChain
     * @return
     */
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        String requestURI = getPathWithinApplication(request);
        if (unFilterSuffix.contains(getUrlSuffix(requestURI))){
            return null;
        }
        if (unFilterUrl.contains(requestURI)) return null;
        return super.getChain(request, response, originalChain);
    }

    private String getUrlSuffix(String url){
        if (StringUtils.isBlank(url)) return null;
        if (!StringUtils.contains(url, ".")) return null;
        return url.substring(url.lastIndexOf("."));
    }

    public Set<String> getUnFilterSuffix() {
        return unFilterSuffix;
    }

    public void setUnFilterSuffix(Set<String> unFilterSuffix) {
        this.unFilterSuffix = unFilterSuffix;
    }

    public Set<String> getAuthFilterUrl() {
        return authFilterUrl;
    }

    public void setAuthFilterUrl(Set<String> authFilterUrl) {
        this.authFilterUrl = authFilterUrl;
    }

    public Set<String> getUserFilterUrl() {
        return userFilterUrl;
    }

    public void setUserFilterUrl(Set<String> userFilterUrl) {
        this.userFilterUrl = userFilterUrl;
    }


}
