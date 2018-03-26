package com.uncub.blog.shiro.matcher;

import com.uncub.blog.common.util.MD5Utils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5CredentialsMatcher implements CredentialsMatcher {

    String algorithmName = "md5";
    int hashIterations = 2;

    /**
     * Returns {@code true} if the provided token credentials match the stored account credentials,
     * {@code false} otherwise.
     *
     * @param token the {@code AuthenticationToken} submitted during the authentication attempt
     * @param info  the {@code AuthenticationInfo} stored in the system.
     * @return {@code true} if the provided token credentials match the stored account credentials,
     * {@code false} otherwise.
     */
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (!(info instanceof SimpleAuthenticationInfo)) throw new RuntimeException("不支持的密码！");
        if (!(token instanceof UsernamePasswordToken)) throw new RuntimeException("不支持的密码！");
        if (!(info.getCredentials() instanceof String)) throw new RuntimeException("不支持的密码！");
        SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo) info;
        String md5 = MD5Utils.getMD5(new String((char[])token.getCredentials()) + new String(simpleAuthenticationInfo.getCredentialsSalt().getBytes()), 2);
        return md5.equals(info.getCredentials());
    }
}
