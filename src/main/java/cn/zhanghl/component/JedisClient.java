package cn.zhanghl.component;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis客户端单机版实现类
 * @author win8.1
 *
 */
public class JedisClient {

	public JedisPool jedisPool;

	public JedisClient(){
		if(jedisPool ==null){
			jedisPool = new JedisPool();
		}

	}


	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}


	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}


	public Long hset(String key, String filed, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, filed,value);
		jedis.close();
		return result;
	}


	public String hget(String key, String filed) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(key,filed);
		jedis.close();
		return result;
	}


	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}


	public Long decr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}


	public Long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key,second);
		jedis.close();
		return result;
	}


	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}


	public Long hdel(String key, String filed) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key,filed);
		jedis.close();
		return result;
	}

}
