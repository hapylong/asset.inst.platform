package com.iqb.asset.inst.platform.common.redis;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * redis dao
 * 
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @param <T>
 * @Date 2016年2月3日-下午2:52:40
 */
@Component
public class RedisPlatformDao<V> {

	/**
	 * 注入template
	 */
	@Autowired
	@Qualifier("redisTemplate_platform1")
	private RedisTemplate<String, String> template;

	/**
	 * key - value
	 * 
	 * @param key
	 * @param value
	 */
	public void setKeyAndValue(String key, String value) {
		template.opsForValue().set(key, value);
	}

	/**
	 * key 存放对象
	 * @param key
	 * @param value
	 */
	public void setKeyAndClass(String key, V value) {
		try {
			String s = getObjectMapper().writeValueAsString(value);
			template.opsForValue().set(key, s);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	public V get(String key, Class<V> clazz){
		String s = template.opsForValue().get(key);
		if(s == null){
		    return null;
		}
		try {
			V v = getObjectMapper().readValue(s, clazz);
			return v;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * key - value - timeout(单位：秒)
	 * 
	 * @param key
	 * @param value
	 */
	public void setKeyAndValueTimeout(String key, String value, long timeout) {
		template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 根据key查询value
	 * 
	 * @param key
	 * @return
	 */
	public String getValueByKey(String key) {
		return template.opsForValue().get(key);
	}

	/**
	 * 根据key删除value
	 * 
	 * @param key
	 */
	public void removeValueByKey(String key) {
		template.delete(key);
	}

	/**
	 * 推送消息
	 * 
	 * @param key
	 * @param value
	 */
	public void leftPush(String key, String value) {
		template.opsForList().leftPush(key, value);
	}

	/**
	 * 消费消息
	 * 
	 * @param key
	 * @return
	 */
	public String rightPop(String key) {
		String value = template.opsForList().rightPop(key);
		if (null == value || "".equals(value))
			return null;
		return value;
	}
	
	/**
	 * 队列长度
	 * 
	 * @param key
	 * @return
	 */
	public long len(String key) {
		Long len = template.opsForList().size(key);
		if (null == len)
			return 0;
		return len;
	}

	/**
	 * 根据key进行模糊匹配查询
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getKeys(String key) {
		return template.keys(key);
	}

	/**
	 * 设置key在指定时间点失效
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public Boolean expireAt(String key, Date date) {
		return template.expireAt(key, date);
	}

	public ObjectMapper getObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return om;
	}
}