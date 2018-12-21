package com.facecourt.webapp.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
		logger.info("configureDefaultServletHandling.");
		configurer.enable();
	}

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		logger.info("addViewControllers.");
		registry.addViewController("/").setViewName("forward:/index");
		registry.addViewController("/index");
		registry.addViewController("/login");
		registry.addViewController("/h2-console");
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		logger.info("addResourceHandlers.");
//		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/");
//		super.addResourceHandlers(registry);
	}

	/**
	 * this is replaced by setting it in application.properties file. Somehow it
	 * does not work with java configuration for message resource.
	 * 
	 * @Bean public MessageSource messageSource() {
	 * 
	 *       ReloadableResourceBundleMessageSource messageSource = new
	 *       ReloadableResourceBundleMessageSource();
	 *       messageSource.setBasenames("classpath:messages",
	 *       "classpath:messages/validation"); // if true, the key of the
	 *       message will be displayed if the key is not // found, instead of
	 *       throwing a NoSuchMessageException
	 *       messageSource.setUseCodeAsDefaultMessage(true);
	 *       messageSource.setDefaultEncoding("UTF-8"); // # -1 : never reload,
	 *       0 always reload messageSource.setCacheSeconds(0); return
	 *       messageSource; }
	 **/

	@Bean
	public LocaleResolver localeResolver() {
		logger.info("localeResolver.");
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		Locale defaultLocale = new Locale("en_US");
		localeResolver.setDefaultLocale(defaultLocale);
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		logger.info("LocaleChangeInterceptor.");
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("addInterceptors.");
		registry.addInterceptor(localeChangeInterceptor());
	}

}