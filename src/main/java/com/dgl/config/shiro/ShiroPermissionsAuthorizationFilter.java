package com.dgl.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.dgl.common.Enum.EnumErrorMsg;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;



@Component
public class ShiroPermissionsAuthorizationFilter extends FormAuthenticationFilter {

    private static final Logger logger= LoggerFactory.getLogger(ShiroPermissionsAuthorizationFilter.class);

    private final static Map<Class, String> exceptionMessageMap;

    static {
        exceptionMessageMap = new HashMap<>();
        exceptionMessageMap.put(IncorrectCredentialsException.class, "账号密码不正确");
        exceptionMessageMap.put(ExpiredCredentialsException.class, "账号密码过期");
        exceptionMessageMap.put(CredentialsException.class, "账号密码异常");
        exceptionMessageMap.put(ConcurrentAccessException.class, "无法同时多个用户登录");
        exceptionMessageMap.put(UnknownAccountException.class, "账号不存在");
        exceptionMessageMap.put(ExcessiveAttemptsException.class, "账号验证次数超过限制");
        exceptionMessageMap.put(LockedAccountException.class, "账号被锁定");
        exceptionMessageMap.put(DisabledAccountException.class, "账号被禁用");
        exceptionMessageMap.put(AccountException.class, "账号异常");
        exceptionMessageMap.put(UnsupportedTokenException.class, "不支持当前TOKEN");
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", EnumErrorMsg.USER_SESSION_ERROR.getCode());
        jsonObject.put("msg", EnumErrorMsg.USER_SESSION_ERROR.getMsg());
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(jsonObject);
        } catch (Exception e) {
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
        return false;
    }

    @Bean
    public FilterRegistrationBean registration(ShiroPermissionsAuthorizationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

}
