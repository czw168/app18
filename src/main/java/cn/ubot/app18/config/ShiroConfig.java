package cn.ubot.app18.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.ubot.app18.shiro.cache.RedisCacheManager;
import cn.ubot.app18.shiro.realms.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author czw
 * @create 2020-08-03 15:11
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 与thymeleaf整合需要用到这个
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * shiro过滤器，拦截所有请求
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 配置系统受限资源
        // 配置系统公共资源
        Map<String,String> map = new HashMap<String,String>();

        // anon：公共资源
        map.put("/login","anon");
        map.put("/user/loginto","anon");
        map.put("/css/**","anon");
        map.put("/img/**","anon");
        map.put("/js/**","anon");
        // authc：需要认证和授权的资源，需要放在anon的下面
        map.put("/**","authc");

        // 默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/");
        // 无访问权限
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * shiro安全管理器
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    /**
     * 自定义Realm
     * @return
     */
    @Bean
    public Realm getRealm(){
        MyRealm myRealm = new MyRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(2);
        myRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理
        myRealm.setCacheManager(new RedisCacheManager());
        myRealm.setCachingEnabled(true);//开启全局缓存
        myRealm.setAuthenticationCachingEnabled(true);//认证认证缓存
        myRealm.setAuthenticationCacheName("authenticationCache");
        myRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        myRealm.setAuthorizationCacheName("authorizationCache");

        return myRealm;
    }

    /**
     * 解决shiro注解无法在controller层使用的问题
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);

        return defaultAdvisorAutoProxyCreator;
    }

}
