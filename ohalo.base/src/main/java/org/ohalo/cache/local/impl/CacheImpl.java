package org.ohalo.cache.local.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ohalo.cache.local.IBaseCache;

public class CacheImpl implements IBaseCache<String, Object> {

	private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

	@Override
	public Object getValue(String key) {
		return cache.get(key);
	}

	@Override
	public void addOrUpdateCache(String key, Object value) {
		cache.put(key, value);
	}

	@Override
	public void evictCache() {
		cache.clear();
	}

	@Override
	public void evictCache(String key) {
		cache.remove(key);
	}
}
