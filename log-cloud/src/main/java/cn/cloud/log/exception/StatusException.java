package cn.cloud.log.exception;

import java.util.HashMap;
import java.util.Map;

import cn.cloud.log.util.JsonUtil;

/**
 * 统一异常
 * @author Administrator
 *
 */
public class StatusException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1368639320367568967L;
	
	private String code;//状态码

	/**
	 * 状态描述
	 */
	private String desc;
    
	/**
	 * 构造函数
	 */
	public StatusException(String code, String desc) {
		super("[code: " + code + "; desc: " + desc + "]");
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 构造函数
	 */
	public StatusException(String code, String desc, Throwable cause) {
		super("[code: " + code + "; desc: " + desc + "]", cause);
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * @return
	 */
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("desc", desc);
		return JsonUtil.toJson(map);
	}

	@Override
	public String toString() {
		return toJson();
	}
}
