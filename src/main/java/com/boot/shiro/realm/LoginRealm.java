package com.boot.shiro.realm;

import java.util.Map;

import com.boot.util.Constants;

import org.apache.shiro.authc.*;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author zxx
 * @Description
 * @Date Created on 2017/11/10
 */
public class LoginRealm extends AuthorizationBaseRealm{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DEFAULT_PWD_KEY = "password";
    private static final String DEFAULT_IDENTITY_KEY = "username";
    private static final String DEFAULT_SALT_KEY = "salt";
    /**
     * 认证-登录
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Map<String, Object> info = null;

        if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken authToken = (UsernamePasswordToken) token;
            info = shiroService.getUserUniqueIdentityAndPassword(authToken.getUsername());
        }

        boolean flag = info == null || info.isEmpty() || info.get(Constants.DEFAULT_IDENTITY_KEY) == null
                || info.get(Constants.DEFAULT_PWD_KEY) == null;
        if (!flag) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    info.get(Constants.DEFAULT_IDENTITY_KEY), info.get(Constants.DEFAULT_PWD_KEY), getName());
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(info.get(DEFAULT_SALT_KEY)));

            logger.info("verify account success. usernaame: {}", info.get(Constants.DEFAULT_IDENTITY_KEY));
            return simpleAuthenticationInfo;
        } else {
            // 没有找到账号
            throw new UnknownAccountException("UnknownAccountException");
        }
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UsernamePasswordToken) {
            return true;
        }
        return false;
    }
}