/**============================================================
 * 版权： 
 * 包： com.after90s.frame.filter
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月26日       LJW        
 * ============================================================*/

package com.after90s.frame.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <p>
 * TODO 国际化过滤器
 * </p>
 * 拦截带有参数lang的url请求
 * 
 * @author LJW
 * @version 2019年7月26日
 */
@SuppressWarnings("deprecation")
@Configuration
public class I18nFilter extends WebMvcConfigurerAdapter  {
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
