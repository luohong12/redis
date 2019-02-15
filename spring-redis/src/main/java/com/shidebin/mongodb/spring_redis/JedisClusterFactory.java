package com.shidebin.mongodb.spring_redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements InitializingBean{
	private Resource addressConfig;
	private String addressKeyPrefix;
	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> nodes = getNodes();
		this.jedisCluster = new JedisCluster(nodes,timeout,maxRedirections,genericObjectPoolConfig);
	}
	private Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		Properties source = new Properties();
		try {
			source.load(this.addressConfig.getInputStream());
			for(Iterator<Object> ite = source.keySet().iterator();ite.hasNext();){
				String key = ite.next().toString();
				if(key.startsWith(addressKeyPrefix)) {
					String val = source.getProperty(key);
					boolean isAddress = p.matcher(val).matches();
					if(!isAddress) {
						throw new IllegalArgumentException("cluster 地址格式不对");
					}
					HostAndPort hap = new HostAndPort(val.split(":")[0],Integer.parseInt(val.split(":")[1]));
					nodes.add(hap);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}
	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}
	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}
	
}
