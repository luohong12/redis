package com.shidebin.mongodb.spring_redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisSentinelTest {
	@Autowired
	public ValueOperations<String,String> stringRedisTemplate;
	@Autowired
	public HashOperations<String,String,String> hashRedisTemplate;
	@Autowired
	public ListOperations<String,String> listRedisTemplate;
	@Autowired
	public SetOperations<String,String> setRedisTemplate;
	@Autowired
	public ZSetOperations<String,String> zSetRedisTemplate;
	@Test
	public void set() {
		//set name shidebin
		stringRedisTemplate.set("name", "shidebin");	
		//保存对象
		User user = User.getUserBuilder().name("shidebin").age(29).sex("男").build();
		String jsonString = JSON.toJSONString(user);
		stringRedisTemplate.set("user:1", jsonString);
		//保存list
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		String listJson = JSON.toJSONString(list);
		stringRedisTemplate.set("list", listJson);
		//保存map
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", "shidebin");
		map.put("age", "29");
		map.put("phone", "18615412359");
		String mapJson = JSON.toJSONString(map);
		stringRedisTemplate.set("map", mapJson);
		//mset country china city beijing
		stringRedisTemplate.multiSet(map);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void get() {
		//取string
		String name = stringRedisTemplate.get("name");
		System.out.println(name);
		//取对象
		String userStr = stringRedisTemplate.get("user:1");
		User parseObject = JSON.parseObject(userStr, User.class);
		System.out.println(userStr);
		System.out.println(parseObject);
		//取list
		String listStr = stringRedisTemplate.get("list");
		List<String> parseObject2 = JSON.parseArray(listStr, String.class);
		System.out.println(listStr);
		System.out.println(parseObject2);
		//取map
		String mapStr = stringRedisTemplate.get("map");
		Map<String,String> parseObject3 = JSON.parseObject(mapStr, Map.class);
		System.out.println(mapStr);
		System.out.println(parseObject3);
		//mget country city address
		List<String> multiGet = stringRedisTemplate.multiGet(Arrays.asList("name","age","phone"));
		System.out.println(multiGet);
	}
	//hash
	@Test
	public void hSet() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", "shidebin");
		map.put("age", "29");
		map.put("phone", "18615412359");
		hashRedisTemplate.putAll("user:2", map);
	}
	@Test
	public void hGet() {
		Map<String, String> entries = hashRedisTemplate.entries("user:2");
		System.out.println(entries);
	}
	//list
	@Test
	public void lpush() {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		listRedisTemplate.leftPushAll("list:1", list);
	}
	@Test
	public void lget() {
		List<String> range = listRedisTemplate.range("list:1", 0, -1);
		System.out.println(range);
	}
	//list
		@Test
		public void sset() {
			List<String> list = new ArrayList<String>();
			list.add("aaa");
			list.add("bbb");
			list.add("ccc");
			setRedisTemplate.add("set:1", list.toArray(new String[] {}));
		}
		@Test
		public void sget() {
			Set<String> members = setRedisTemplate.members("set:1");
			System.out.println(members);
		}
		//list
		@Test
		public void zpush() {
			Set<TypedTuple<String>> set = new HashSet<TypedTuple<String>>();
			TypedTuple<String> tt = new DefaultTypedTuple<String>("shidebin",Double.valueOf(23));
			set.add(tt);
			zSetRedisTemplate.add("zset",set);
		}
		@Test
		public void zget() {
			Set<TypedTuple<String>> rangeWithScores = zSetRedisTemplate.rangeWithScores("zset", 0, -1);
			rangeWithScores.stream().forEach(action -> {
				System.out.println(action.getScore());
				System.out.println(action.getValue());
			});
		}
}
