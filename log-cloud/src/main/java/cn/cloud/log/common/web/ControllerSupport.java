package cn.cloud.log.common.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
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
    
	
	/**
	 * 转换为数据库模糊查询匹配模式
	 *
	 * @author WANGWEI
	 * @param column
	 * @return
	 */
	protected String toSqlSearchPattern(String column) {
		if (StringUtils.isEmpty(column)) {
			return "%";
		} else {
			column = column.trim().replaceAll("\\s", "%");
			column = "%" + column + "%";
			return column;
		}
	}
    
	
	protected void exportFile(String fileName, File file) throws IOException {
		OutputStream out = null;
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			HttpServletResponse response = getResponse();
			response.reset();
			response.setHeader("Content-Disposition", "inline; filename=" + fileName);
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream;charset=UTF-8");
			out = new BufferedOutputStream(response.getOutputStream());
			IOUtils.copy(in, out);
			out.flush();
		} catch (IOException e) {
			throw new StatusException("229",e.getMessage());
		} finally {
			out.close();
			in.close();
		}
	}
	
	

}