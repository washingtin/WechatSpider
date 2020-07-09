package com.seven.core;

import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务器返值包装
 * 
 * @author 最爱吃小鱼
 *
 */
@SuppressWarnings("serial")
public class ResponseEntity implements Serializable{
	
	// 状态代码
    protected Integer code;
    // 消息内容
    protected String message;
	// 返回的数据
    private Object data;
    
	public static ResponseEntity success(Object data) {
		if (data instanceof ResponseEntity) {
			return (ResponseEntity)data;
		}
		ResponseEntity entity = new ResponseEntity();
		entity.setCode(1);
		entity.setMessage("成功");
		entity.setData(data);
		return entity;
	}
	
	public static ResponseEntity err() {
		ResponseEntity entity = new ResponseEntity();
		entity.setCode(0);
		entity.setMessage("服务器超时，请稍候再试");
		return entity;
	}
	
	public static ResponseEntity err(Integer code, String message) {
		ResponseEntity entity = new ResponseEntity();
		entity.setCode(code);
		entity.setMessage(message);
		return entity;
	}
	
	public static ResponseEntity page(Page<?> page) {
		ResponseEntity entity = new ResponseEntity();
		entity.setCode(1);
		entity.setMessage("成功");
		Map<String, Object> map = new HashMap<>();
		map.put("list", page.getRecords());
		map.put("total", page.getTotal());
		entity.setData(map);
		return entity;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
