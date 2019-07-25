package cn.cloud.log.common.web.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import cn.cloud.log.exception.StatusException;

public final class SimpleRedisClient implements RedisClient {

	

	private RedisTemplate<String, Object> redisTemplate;

	private boolean enable = true;

	public SimpleRedisClient(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	private void beforeMethod() {
		if (!enable) {
			throw new StatusException("225","RedisClient is not enabled");
		}
	}

	private void afterMethod(String method, long startTimeMillis) {
		String s = String.format("[SimpleRedisClient.%s] cost %d ms.", method,
				System.currentTimeMillis() - startTimeMillis);
		System.out.println(s);
	}

	@Override
	public void set(String key, Object value) {
		long s = System.currentTimeMillis();
		beforeMethod();
		redisTemplate.opsForValue().set(key, value);
		afterMethod("set(String key, Object value)", s);
	}

	@Override
	public void set(String key, Object value, int timeout) {
		long s = System.currentTimeMillis();
		beforeMethod();
		set(key, value);
		expire(key, timeout);
		afterMethod("set(String key, Object value, int timeout)", s);
	}

	@Override
	public void expire(String key, int timeout) {
		long s = System.currentTimeMillis();
		beforeMethod();
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		afterMethod("expire(String key, int timeout)", s);
	}

	/**
	 * 方法注释
	 *
	 * @author WANGWEI
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void expire(String key, final long timeout, final TimeUnit unit) {
		long s = System.currentTimeMillis();
		beforeMethod();
		redisTemplate.expire(key, timeout, unit);
		afterMethod("(String key, final long timeout, final TimeUnit unit)", s);
	}

	@Override
	public <T> T get(String key, Class<T> c, int timeout) {
		long s = System.currentTimeMillis();
		beforeMethod();
		Object object = redisTemplate.opsForValue().get(key);
		@SuppressWarnings("unchecked")
		T t = (T) object;
		expire(key, timeout);
		afterMethod("get(String key, Class<T> c, int timeout)", s);
		return t;
	}

	@Override
	public <T> T get(String key, Class<T> c) {
		long s = System.currentTimeMillis();
		beforeMethod();
		Object object = redisTemplate.opsForValue().get(key);
		@SuppressWarnings("unchecked")
		T t = (T) object;
		afterMethod("get(String key, Class<T> c)", s);
		return t;
	}

	@Override
	public void delete(String key) {
		long s = System.currentTimeMillis();
		beforeMethod();
		redisTemplate.opsForValue().set(key, null);
		expire(key, 0);
		afterMethod("delete(String key)", s);
	}

	@Override
	public void convertAndSend(String channel, Object message) {
		long s = System.currentTimeMillis();
		beforeMethod();
		redisTemplate.convertAndSend(channel, message);
		afterMethod("convertAndSend(String channel, Object message)", s);
	}

	@Override
	public Boolean setIfAbsent(String key, String value, int timeout) {
		long s = System.currentTimeMillis();
		beforeMethod();
		Boolean b = redisTemplate.opsForValue().setIfAbsent(key, value);
		if (b) {
			expire(key, timeout);
		}
		afterMethod("setIfAbsent(String key, String value, int timeout)", s);
		return b;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	

}
