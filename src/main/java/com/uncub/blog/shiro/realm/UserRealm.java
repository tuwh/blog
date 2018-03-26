package com.uncub.blog.shiro.realm;

import com.uncub.blog.dto.base.User;
import com.uncub.blog.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userNo = (String) principals.getPrimaryPrincipal();
        User userQueryBean = new User();
        userQueryBean.setUserNo(userNo);
        List<User> users = userService.queryUser(userQueryBean);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.getEffictiveRolesByUserId(users.get(0).getId()));
        authorizationInfo.setStringPermissions(userService.findPermissionByUserId(users.get(0).getId()));
        return authorizationInfo;
    }


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userNo = (String) token.getPrincipal();
        User userQueryBean = new User();
        userQueryBean.setUserNo(userNo);
        List<User> users = userService.queryUser(userQueryBean);
        if (users.isEmpty()) {
            throw new UnknownAccountException("无此账号！");//没找到帐号
        }
        User user = users.get(0);
        if (Boolean.TRUE.equals(user.getStatus())) {
            throw new LockedAccountException(); //帐号锁定
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //用户名
                user.getPassword(), //密码
                new SimpleByteSource(user.getUserNo() + user.getSalt()),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
