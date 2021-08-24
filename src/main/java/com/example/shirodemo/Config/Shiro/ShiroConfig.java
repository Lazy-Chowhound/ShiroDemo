package com.example.shirodemo.Config.Shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Nakano Miku
 */
@Configuration
public class ShiroConfig {

    /**
     * 注意这里的SecurityManager是org.apache.shiro.mgt.SecurityManager这个包下面的
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(userRealm);
        webSecurityManager.setRememberMeManager(null);
        return webSecurityManager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, AccessFilter accessFilter) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        // 未认证时跳转的url
        factoryBean.setLoginUrl("/user/notAllowed");

        // 添加自定义filter
        Map<String, Filter> filters = new HashMap<>();
        filters.put("accessFilter", accessFilter);
        factoryBean.setFilters(filters);

        //设置拦截顺序和拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/user/display", "authc");
        // 使用自定义filter []里为需要的角色权限，可不写
        filterChainDefinitionMap.put("/user/area", "accessFilter[ROOT,R6]");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        factoryBean.setSuccessUrl("/user/homepage");

        return factoryBean;
    }

    /**
     * 取消自动注册自定义filter
     */
    @Bean
    public FilterRegistrationBean<AccessFilter> oAuth2FilterRegistration(AccessFilter accessFilter) {
        FilterRegistrationBean<AccessFilter> filterRegistrationBean = new FilterRegistrationBean<>(accessFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

    // 如果@RequiresAuthentication和@RequiresPermissions不生效 添加下面三个函数

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
