package com.wujie.app.framework.cache.ecache;

import com.wujie.app.framework.util.spring.SpringContextUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

public class CacheUtils {


	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}

	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}

	@SuppressWarnings("rawtypes")
	public static List cacheKeys(String cacheName) {
		return getCache(cacheName).getKeys();
	}

	/**
	 * 获得一个Cache，没有则创建一个。
	 * 
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName) {
		CacheManager cacheManager = (CacheManager) SpringContextUtil.getApplicationContext()
				.getBean("ehCacheCacheManager");
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		CacheManager cacheManager = (CacheManager) SpringContextUtil.getApplicationContext()
				.getBean("ehCacheCacheManager");
		return cacheManager;
	}

}
