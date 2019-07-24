package cn.cloud.log.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.cloud.log.basic.bean.User;
import cn.cloud.log.exception.StatusException;

public abstract class ControllerSupport {

	/**
	 * controller 统一业务日志对象
	 */


	/**
	 * 获取接入用户
	 * 
	 * @return
	 */
	protected User getAccessUser() {
		HttpServletRequest request=getRequest();
//		User accessUser = (User)request
//				.getAttribute(HttpServletRequestAttribute.$_ACCESS_USER.name());
//		if (null == accessUser) {
//			throw new StatusException("252", "请重新登陆");
//		}
		return null;
	}	
	/**
	 * 获取request对象
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
	}

	/**
	 * 获取response对象
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletResponse response = requestAttributes.getResponse();
		return response;
	}

	
	

}