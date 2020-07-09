package com.seven.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Iterator;
import java.util.List;


@EnableWebMvc
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	static Logger log = LoggerFactory.getLogger(MyWebMvcConfigurerAdapter.class);
	
	@Autowired
    private ApplicationContext context;

	/**
	 * 	1. 删除StringHttpMessageConverter & MappingJackson2HttpMessageConverter默认的消息处理器<br/>
	 *  2. 目的 请查看{@link MyResponseBodyAdvice#beforeBodyWrite} 对返回的消息进行了进一步的封装
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		Iterator<HttpMessageConverter<?>> itr = converters.iterator();
		HttpMessageConverter<?> hmc;
		while (itr.hasNext()) {
			hmc = itr.next();
			if (hmc instanceof StringHttpMessageConverter || hmc instanceof MappingJackson2HttpMessageConverter) {
				itr.remove();
			}
		}
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		/**
		 * 序列换成json时,将所有的long变成string
		 * 因为js中得数字类型不能包含所有的java long值
		 * 资料:http://www.xaazg.com/480495926.html
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		jackson2HttpMessageConverter.setObjectMapper(objectMapper);


		converters.add(jackson2HttpMessageConverter);
		
		super.extendMessageConverters(converters);
		log.debug("移除StringHttpMessageConverter, 任务交由自定义的MappingJackson2HttpMessageConverter处理");
	}

	/**
	 * 静态资源过滤
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/webapp/release/");
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

}
