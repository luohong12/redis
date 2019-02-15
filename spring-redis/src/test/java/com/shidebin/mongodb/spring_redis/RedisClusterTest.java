package com.shidebin.mongodb.spring_redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.JedisCluster;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisClusterTest {
	 @Autowired
	 JedisClusterFactory jf;
	 @Test
	 public void testCluster() {
		 JedisCluster jedisCluster = jf.getJedisCluster();
		 String set = jedisCluster.set("abd".getBytes(), "fasdfjklasd".getBytes());
		 System.out.println(set);
	 }
	 
}
