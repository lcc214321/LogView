package cn.cloud.log.common.web.redis;

import java.util.concurrent.TimeUnit;

/**
 * redis client
 *
 * @author WANGWEI
 * @date 2019年2月22日
 * @Copyright (c) 2018-2020 WANGWEI [QQ:522080330] All Rights Reserved.
 */
public interface RedisClient {

	/**
	 * 是否可用
	 *
	 * @author WANGWEI
	 * @return
	 */
	public boolean isEnable();

	/**
	 * 设置是否可用
	 *
	 * @author WANGWEI
	 * @param enable
	 */
	public void setEnable(boolean enable);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void set(String key, Object value, int timeout);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param timeout
	 */
	public void expire(String key, int timeout);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void expire(String key, final long timeout, final TimeUnit unit);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param c
	 * @param timeout
	 * @return
	 */
	public <T> T get(String key, Class<T> c, int timeout);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param c
	 * @return
	 */
	public <T> T get(String key, Class<T> c);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param channel
	 * @param message
	 */
	public void convertAndSend(String channel, Object message);

	/**
	 * (在key不存在时,创建并设置value 返回true; key存在时,会反回false)
	 *
	 * @author WANGWEI
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public Boolean setIfAbsent(String key, String value, int timeout);

}
