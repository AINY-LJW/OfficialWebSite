/**============================================================
 * 版权： 
 * 包： com.after90s.frame.config
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.frame.config;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.after90s.frame.shiro.realm.MyShiroRealm;

import net.sf.ehcache.CacheManager;

/**
 * <p>
 * TODO Shiro配置
 * 带有 @Bean 注解的方法名称作为 bean 的 ID，它创建并返回实际的 bean。你的配置类可以声明多个 @Bean
 * </p>
 *
 * @author lijiawen
 * @version 2019年7月31日
 */
@Configuration
public class ShiroConfig {
	/**
	 * Shiro过滤器配置
	 * 配置哪些资源需要校验是否登录
	 * 
	 * @param securityManager
	 * @return ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {

		System.out.println("ShiroConfiguration.shirFilter()");

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 设置login URL
		shiroFilterFactoryBean.setLoginUrl("/login");

		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/LoginSuccess");
		// 未授权的页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

		// src="jquery/jquery-3.2.1.min.js" 生效
		filterChainDefinitionMap.put("/css/*", "anon");
		filterChainDefinitionMap.put("/js/*", "anon");
		filterChainDefinitionMap.put("/fonts/*", "anon");
		filterChainDefinitionMap.put("/img/*", "anon");
		// 不需要验证是否登录的 设置登录的URL为匿名访问，因为一开始没有用户验证
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/Exception.class", "anon");

		// 需要验证的
		filterChainDefinitionMap.put("/*.action", "authc");
		// 注销过滤器
		filterChainDefinitionMap.put("/logout", "logout");

		// 现在资源的角色
		filterChainDefinitionMap.put("/admin.html", "roles[admin]");

		// 最后一班都，固定格式
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}
	/*
	 * 凭证匹配器 
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; )
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法，MD5
		hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于md5(md5(""));
		return hashedCredentialsMatcher;
	}
	
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}
	
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 注入自定义的realm;
		securityManager.setRealm(myShiroRealm());
		// 注入缓存管理器;
		securityManager.setCacheManager(ehCacheManager());
 
		return securityManager;
	}
	
	/*
	 * 开启shiro aop注解支持 使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}
	
	/* shiro缓存管理器;
	 * 需要注入对应的其它的实体类中-->安全管理器：securityManager可见securityManager是整个shiro的核心；
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		System.out.println("ShiroConfiguration.getEhCacheManager()");
//		EhCacheManagerFactoryBean bean=new EhCacheManagerFactoryBean();
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManager(ehCacheManagerFactoryBean());
		cacheManager.setCacheManagerConfigFile("classpath:ehcache/shiro-ehcache.xml");
		return cacheManager;
	}
	/**
     * 管理缓存 解决热部署 Ehcache重复创建CacheManager问题
     * @return
     */
    @Bean(name = "ehcacheManager")
    public CacheManager ehCacheManagerFactoryBean() {
        CacheManager cacheManager = CacheManager.getCacheManager("es");
        if(cacheManager == null){
            try {
                cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:ehcache/shiro-ehcache.xml"));
            } catch (IOException e) {
                throw new RuntimeException("initialize cacheManager failed");
            }
        }
        return cacheManager;
    }

}
