package com.dgl.config.shiro;

import com.dgl.common.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @ClassName ShiroConfig
 * @Description: TODO
 * @Author shiro拦截器
 * @Date 2020/4/6
 * @Version V1.0
 */

@Slf4j
@Configuration
@Data
public class ShiroConfig {

    /**
     * 1.创建过滤器,负责拦截所有请求
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {

        //1.创建shiro过滤器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //2.给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());

        //3.配置系统受限资源--定义shiro过滤链
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/user/register/**", "anon");
        filterChainDefinitionMap.put("/user/login/**", "anon");

        //4.authc 请求这个资源需要认证和授权
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 2.安全管理器
     */
    @Bean
    public DefaultWebSecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义session管理
        securityManager.setSessionManager(sessionManager());
        //设置redis缓存
        securityManager.setCacheManager(cacheManager());
        //设置realm
        securityManager.setRealm(getRealm());
        return securityManager;
    }

    /**
     * 3.创建自定义realm
     */
    @Bean
    public CustomerRealm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        return customerRealm;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis, 使用的是shiro-redis开源插件
     *
     * @return RedisSessionDAO
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setExpire(-1);
        return redisSessionDAO;
    }

    /**
     * Session ID 生成器
     *
     * @return JavaUuidSessionIdGenerator
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


    /**
     * 自定义sessionManager
     *
     * @return SessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        //配置默认的session管理器
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        //默认7天
        mySessionManager.setGlobalSessionTimeout(604800000L);
        return mySessionManager;
    }


    @Bean
    public RedisParamConfig redisParamConfig() {
        return new RedisParamConfig();
    }


    /**
     * 配置shiro redisManager, 使用的是shiro-redis开源插件
     *
     * @return RedisManager
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisParamConfig().getHost());
        redisManager.setPort(redisParamConfig().getPort());
        redisManager.setTimeout(604800000);
        return redisManager;
    }


    /**
     * cacheManager 缓存 redis实现, 使用的是shiro-redis开源插件
     *
     * @return RedisCacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("id");
        redisCacheManager.setExpire(604800);
        return redisCacheManager;
    }
//
//
//
//    /**
//     * 凭证匹配器
//     */
//    @Bean(name = "credentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        //散列算法--这里使用MD5算法
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setHashIterations(1);
//        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return hashedCredentialsMatcher;
//    }
//
//
//    /**
//     * Shiro生命周期处理器
//     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//
//    /*
//     * 开启Shiro的注解(如@RequiresRoles, @RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
//     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
//     */
//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
//        SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//
//    @Bean(name = "sessionIdCookie")
//    public SimpleCookie rememberMeCookie() {
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
//        //setcookie()的第七个参数
//        //设为true后，只能通过http访问，javascript无法访问
//        //防止xss读取cookie
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setPath("/");
//        //记住我cookie生效时间30天,单位秒
//        simpleCookie.setMaxAge(2592000);
//        return simpleCookie;
//    }
//
//    /**
//     * cookie管理对象;记住我功能,rememberMe管理器
//     *
//     * @return
//     */
//    @Bean
//    public CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
//        return cookieRememberMeManager;
//    }
//
//    /**
//     * FormAuthenticationFilter 过滤器 过滤记住我
//     *
//     * @return
//     */
//    @Bean
//    public FormAuthenticationFilter formAuthenticationFilter() {
//        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
//        //对应前端的checkbox的name = rememberMe
//        formAuthenticationFilter.setRememberMeParam("rememberMe");
//        return formAuthenticationFilter;
//    }

}




