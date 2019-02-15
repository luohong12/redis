package com.shidebin.mongodb.spring_redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateImpl{
	@Autowired
	RedisTemplate<String,Object> redisTemplate;
	private HashOperations<String, Object, Object> opsForHash;
	private ListOperations<String, Object> opsForList;
	private SetOperations<String, Object> opsForSet;
	private ZSetOperations<String, Object> opsForZSet;
	public RedisTemplateImpl() {
		
		this.opsForHash = redisTemplate.opsForHash();
		this.opsForList = redisTemplate.opsForList();
		this.opsForSet = redisTemplate.opsForSet();
		this.opsForZSet = redisTemplate.opsForZSet();
	}
	//set name shidebin
		public void set(String key,Object value) {
			BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps(key);
			boundValueOps.set(value);
		}
		// set name shidebin ex 10
		public void set(String key,Object value,long timeout) {
			BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps(key);
			boundValueOps.set(value,timeout,TimeUnit.MILLISECONDS);
		}
		//type name：键类型（String，list，hash，set，sort set）
		public DataType getType(String key) {
			BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps(key);
			return boundValueOps.getType();
		}
		//批量设值：mset country china city beijing
		public void Mset(String key,Object ...value) {
		}
	//String
	private class stringTemplate{
		private ValueOperations<String, Object> opsForValue;
		public stringTemplate() {
			this.opsForValue = redisTemplate.opsForValue();
		}
		//set name shidebin
		public void set(String key,Object value) {
			opsForValue.set(key,value);
		}
		// set name shidebin ex 10
		public void set(String key,Object value,long timeout) {
			BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps(key);
			boundValueOps.set(value,timeout,TimeUnit.MILLISECONDS);
		}
		//type name：键类型（String，list，hash，set，sort set）
		public DataType getType(String key) {
			BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps(key);
			return boundValueOps.getType();
		}
		//批量设值：mset country china city beijing
		public void Mset(String key,Object ...value) {
		}
	}
	
	
}
