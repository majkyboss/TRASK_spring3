package com.websystique.springmvc.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.websystique.springmvc")
@Import({ SecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver() {
		// InternalResourceViewResolver viewResolver = new
		// InternalResourceViewResolver();
		// viewResolver.setViewClass(JstlView.class);
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		// viewResolver.setViewClass(FreeMarkerView.class);
		viewResolver.setCache(false);
		viewResolver.setPrefix("");
		// viewResolver.setSuffix(".jsp");
		viewResolver.setSuffix(".ftl");
		return viewResolver;
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfig() {
		// FreeMarkerConfigurationFactoryBean beanFactory = new
		// FreeMarkerConfigurationFactoryBean();
		// beanFactory.setTemplateLoaderPath("/WEB-INF/views/");

		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		// config.setConfiguration(beanFactory.createConfiguration());
		config.setTemplateLoaderPath("/WEB-INF/views/");
		return config;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
}
