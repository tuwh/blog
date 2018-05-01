package com.uncub.blog.common.spring.springmvc;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ShiroExceptionResolver extends AbstractHandlerExceptionResolver {

    private int order;

    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof ShiroException){
            /*Map<String, Object> model = new HashMap();
            model.put("errMsg", ex.getMessage());
            model.put("statck", ex.getStackTrace());
            model.put("exception", ex);*/
            return new ModelAndView("/framework/shiroException", "ex", ex);
        }
        return null;
    }

    @Override
    protected boolean shouldApplyTo(HttpServletRequest request, Object handler) {
        return true;
    }

    @Override
    public int getOrder() {
        return super.getOrder();
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
