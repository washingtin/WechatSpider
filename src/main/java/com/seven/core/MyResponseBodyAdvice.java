package com.seven.core;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.collections.MapUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

/**
 * controller拦截器，处理异常日志记录及返回，重写返回值
 * 
 * @author 最爱吃小鱼
 *
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {
	
	private static final String STATUS = "status";
	private static final String ERROR = "error";

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
	
	// 返回数据的封装
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
		if (body == null) {
			return ResponseEntity.success(body);
		}
		if (body instanceof Page) {
			return ResponseEntity.page((Page<?>)body);
		}
		if (body instanceof LinkedHashMap ) {
			LinkedHashMap<?,?> map = (LinkedHashMap<?,?>)body;
			if (map.containsKey(STATUS) && map.containsKey(ERROR)) {
				return ResponseEntity.err(MapUtils.getInteger(map, STATUS), MapUtils.getString(map, ERROR));
			}
		}
		return ResponseEntity.success(body);
	}
    
   
}
