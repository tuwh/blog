package com.uncub.blog.common.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Nameable;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;

import javax.servlet.Filter;
import java.util.Map;

/**
 * shiro filter管理器，需保持单例
 */
public class UncubFilterChainManager extends DefaultFilterChainManager {
    private Map<String, String> filterChainDefinitionMap;
    private Map<String, Filter> extFilters;

    /**
     * 登陆页面
     */
    private String loginUrl = "login.jsp";

    /**
     * 成功页面
     */
    private String successUrl = "index.vm";

    private boolean isInit = false;

    public void init() {
        synchronized (this){
            if (isInit){
                return;
            }
            initFilters();
            isInit = true;
        }
    }

    private void initFilters(){
        Map<String, Filter> defaultFilters = getFilters();
        //默认过滤器
        for (Filter filter : defaultFilters.values()) {
//            applyGlobalPropertiesIfNecessary(filter);
        }
        //加载自定义过滤器
        Map<String, Filter> filters = getExtFilters();
        if (!CollectionUtils.isEmpty(filters)) {
            for (Map.Entry<String, Filter> entry : filters.entrySet()) {
                String name = entry.getKey();
                Filter filter = entry.getValue();
//                applyGlobalPropertiesIfNecessary(filter);
                if (filter instanceof Nameable) {
                    ((Nameable) filter).setName(name);
                }
                addFilter(name, filter, false);
            }
        }
        //配置过滤器映射
        Map<String, String> chains = getFilterChainDefinitionMap();
        if (!CollectionUtils.isEmpty(chains)) {
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue();
                createChain(url, chainDefinition);
            }
        }
        if (StringUtils.isNotBlank(loginUrl)){
            for(Filter filter : getFilters().values()){
                if (filter instanceof AccessControlFilter){
                    ((AccessControlFilter) filter).setLoginUrl(loginUrl);
                }
            }
        }
        if (StringUtils.isNotBlank(successUrl)){
            for(Filter filter : getFilters().values()){
                if (filter instanceof AuthenticationFilter){
                    ((AuthenticationFilter) filter).setSuccessUrl(successUrl);
                }
            }
        }
    }

    public Map<String, String> getFilterChainDefinitionMap() {
        return filterChainDefinitionMap;
    }

    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        this.filterChainDefinitionMap = filterChainDefinitionMap;
    }

    public Map<String, Filter> getExtFilters() {
        return extFilters;
    }

    public void setExtFilters(Map<String, Filter> extFilters) {
        this.extFilters = extFilters;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
}
