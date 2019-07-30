/**============================================================
 * 包： com.after90s.frame.config
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月26日       lijiawen        
 * ============================================================*/

package com.after90s.frame.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * <p>TODO i18n国际化配置类</p>
 * @author lijiawen
 * @version 2019年7月26日
 */
@Configuration
public class I18nConfig {
	private static Logger logger = LoggerFactory.getLogger(I18nConfig.class);
	  @Bean(name="localeResolver")
	    public LocaleResolver localeResolverBean() {
	        logger.info("#####sessionLocaleResolver---create");
	        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
	        sessionLocaleResolver.setDefaultLocale(Locale.CHINA);
	        logger.info("#####sessionLocaleResolver:"+sessionLocaleResolver);
	        return sessionLocaleResolver;
	    }

	    @Bean(name="messageSource")
	    public ResourceBundleMessageSource resourceBundleMessageSource(){
	        ResourceBundleMessageSource source=new ResourceBundleMessageSource();
	        source.setBasename("i18n/messages");
	        source.setDefaultEncoding("UTF-8");
	        source.setUseCodeAsDefaultMessage(true);
	        return source;
	    }
}
