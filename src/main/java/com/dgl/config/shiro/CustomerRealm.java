package com.dgl.config.shiro;

import com.dgl.common.Constants;
import com.dgl.common.utils.ApplicationContextUtils;
import com.dgl.common.utils.ShiroUtils;
import com.dgl.service.UserService;
import com.dgl.model.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


/**
 * 自定义realm
 */
public class CustomerRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.err.println("===========开始认证===============");
        //根据身份信息(账号)
        String principal = (String) token.getPrincipal();
        //在工厂中获取service对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.findByMobile(principal);
        if (user != null) {
            //往session中塞用户信息
            ShiroUtils.setSessionAttribute(Constants.SESSION_USER_INFO,user);
            return new SimpleAuthenticationInfo(user.getMobile(), user.getPassword(),
                    new MyByteSource(user.getSalt()),
                    this.getName());
        }
        return null;
    }

}
