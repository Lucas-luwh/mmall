package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @package: com.mmall.common
 * @author: luweihong
 * @description: token缓存类
 * @create: 2020/11/15:20:53
 * @version: 1.0
 */
public class TokenCache {

	private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

	public static final String TOKEN_PREFIX = "token_";
	//lru算法
	private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
			.build(new CacheLoader<String, String>() {
				//get取值不到，调用方法进行加载
				@Override
				public String load(String s) throws Exception {
					return "null";
				}
			});
	public static void setKey(String key,String value){
		localCache.put(key,value);
	}

	public static String getKey(String key){
		String value = null;
		try {
			value = localCache.get(key);
			if ("null".equals(value)){
				return null;
			}
			return value;
		} catch (ExecutionException e) {
			logger.error("localCache get error",e);
		}
		return null;
	}

}
